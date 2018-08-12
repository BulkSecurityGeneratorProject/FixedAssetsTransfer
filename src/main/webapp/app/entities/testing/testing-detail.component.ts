import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITesting } from 'app/shared/model/testing.model';

@Component({
    selector: 'jhi-testing-detail',
    templateUrl: './testing-detail.component.html'
})
export class TestingDetailComponent implements OnInit {
    testing: ITesting;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ testing }) => {
            this.testing = testing;
        });
    }

    previousState() {
        window.history.back();
    }
}
