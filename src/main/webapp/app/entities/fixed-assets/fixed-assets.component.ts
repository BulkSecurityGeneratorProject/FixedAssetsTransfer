import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IFixedAssets } from 'app/shared/model/fixed-assets.model';
import { Principal } from 'app/core';
import { FixedAssetsService } from './fixed-assets.service';

@Component({
    selector: 'jhi-fixed-assets',
    templateUrl: './fixed-assets.component.html'
})
export class FixedAssetsComponent implements OnInit, OnDestroy {
    fixedAssets: IFixedAssets[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private fixedAssetsService: FixedAssetsService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private activatedRoute: ActivatedRoute,
        private principal: Principal
    ) {
        this.currentSearch =
            this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search']
                ? this.activatedRoute.snapshot.params['search']
                : '';
    }

    loadAll() {
        if (this.currentSearch) {
            this.fixedAssetsService
                .search({
                    query: this.currentSearch
                })
                .subscribe(
                    (res: HttpResponse<IFixedAssets[]>) => (this.fixedAssets = res.body),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
        this.fixedAssetsService.query().subscribe(
            (res: HttpResponse<IFixedAssets[]>) => {
                this.fixedAssets = res.body;
                this.currentSearch = '';
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    search(query) {
        if (!query) {
            return this.clear();
        }
        this.currentSearch = query;
        this.loadAll();
    }

    clear() {
        this.currentSearch = '';
        this.loadAll();
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInFixedAssets();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IFixedAssets) {
        return item.id;
    }

    registerChangeInFixedAssets() {
        this.eventSubscriber = this.eventManager.subscribe('fixedAssetsListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
