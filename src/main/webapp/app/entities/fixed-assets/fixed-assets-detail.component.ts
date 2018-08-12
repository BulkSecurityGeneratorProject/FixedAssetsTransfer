import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFixedAssets } from 'app/shared/model/fixed-assets.model';

@Component({
    selector: 'jhi-fixed-assets-detail',
    templateUrl: './fixed-assets-detail.component.html'
})
export class FixedAssetsDetailComponent implements OnInit {
    fixedAssets: IFixedAssets;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ fixedAssets }) => {
            this.fixedAssets = fixedAssets;
        });
    }

    previousState() {
        window.history.back();
    }
}
