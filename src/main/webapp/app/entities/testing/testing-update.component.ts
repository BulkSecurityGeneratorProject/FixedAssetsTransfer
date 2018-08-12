import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ITesting } from 'app/shared/model/testing.model';
import { TestingService } from './testing.service';

@Component({
    selector: 'jhi-testing-update',
    templateUrl: './testing-update.component.html'
})
export class TestingUpdateComponent implements OnInit {
    private _testing: ITesting;
    isSaving: boolean;

    constructor(private testingService: TestingService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ testing }) => {
            this.testing = testing;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.testing.id !== undefined) {
            this.subscribeToSaveResponse(this.testingService.update(this.testing));
        } else {
            this.subscribeToSaveResponse(this.testingService.create(this.testing));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ITesting>>) {
        result.subscribe((res: HttpResponse<ITesting>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get testing() {
        return this._testing;
    }

    set testing(testing: ITesting) {
        this._testing = testing;
    }
}
