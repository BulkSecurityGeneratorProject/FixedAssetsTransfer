import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFixedAssets } from 'app/shared/model/fixed-assets.model';
import { FixedAssetsService } from './fixed-assets.service';

@Component({
    selector: 'jhi-fixed-assets-delete-dialog',
    templateUrl: './fixed-assets-delete-dialog.component.html'
})
export class FixedAssetsDeleteDialogComponent {
    fixedAssets: IFixedAssets;

    constructor(
        private fixedAssetsService: FixedAssetsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.fixedAssetsService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'fixedAssetsListModification',
                content: 'Deleted an fixedAssets'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-fixed-assets-delete-popup',
    template: ''
})
export class FixedAssetsDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ fixedAssets }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(FixedAssetsDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.fixedAssets = fixedAssets;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
