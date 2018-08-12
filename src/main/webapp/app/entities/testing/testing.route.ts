import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Testing } from 'app/shared/model/testing.model';
import { TestingService } from './testing.service';
import { TestingComponent } from './testing.component';
import { TestingDetailComponent } from './testing-detail.component';
import { TestingUpdateComponent } from './testing-update.component';
import { TestingDeletePopupComponent } from './testing-delete-dialog.component';
import { ITesting } from 'app/shared/model/testing.model';

@Injectable({ providedIn: 'root' })
export class TestingResolve implements Resolve<ITesting> {
    constructor(private service: TestingService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((testing: HttpResponse<Testing>) => testing.body));
        }
        return of(new Testing());
    }
}

export const testingRoute: Routes = [
    {
        path: 'testing',
        component: TestingComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fixedAssetsTransferApp.testing.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'testing/:id/view',
        component: TestingDetailComponent,
        resolve: {
            testing: TestingResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fixedAssetsTransferApp.testing.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'testing/new',
        component: TestingUpdateComponent,
        resolve: {
            testing: TestingResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fixedAssetsTransferApp.testing.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'testing/:id/edit',
        component: TestingUpdateComponent,
        resolve: {
            testing: TestingResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fixedAssetsTransferApp.testing.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const testingPopupRoute: Routes = [
    {
        path: 'testing/:id/delete',
        component: TestingDeletePopupComponent,
        resolve: {
            testing: TestingResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fixedAssetsTransferApp.testing.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
