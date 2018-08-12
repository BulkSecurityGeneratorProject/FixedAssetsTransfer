import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IFixedAssets } from 'app/shared/model/fixed-assets.model';
import { FixedAssetsService } from './fixed-assets.service';

@Component({
    selector: 'jhi-fixed-assets-update',
    templateUrl: './fixed-assets-update.component.html'
})
export class FixedAssetsUpdateComponent implements OnInit {
    private _fixedAssets: IFixedAssets;
    isSaving: boolean;
    dateDp: any;

    constructor(private fixedAssetsService: FixedAssetsService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ fixedAssets }) => {
            this.fixedAssets = fixedAssets;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.fixedAssets.id !== undefined) {
            this.subscribeToSaveResponse(this.fixedAssetsService.update(this.fixedAssets));
        } else {
            this.subscribeToSaveResponse(this.fixedAssetsService.create(this.fixedAssets));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IFixedAssets>>) {
        result.subscribe((res: HttpResponse<IFixedAssets>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get fixedAssets() {
        return this._fixedAssets;
    }

    set fixedAssets(fixedAssets: IFixedAssets) {
        this._fixedAssets = fixedAssets;
    }
}
