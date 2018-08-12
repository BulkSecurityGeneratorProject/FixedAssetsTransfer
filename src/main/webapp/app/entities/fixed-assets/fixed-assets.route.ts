import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { FixedAssets } from 'app/shared/model/fixed-assets.model';
import { FixedAssetsService } from './fixed-assets.service';
import { FixedAssetsComponent } from './fixed-assets.component';
import { FixedAssetsDetailComponent } from './fixed-assets-detail.component';
import { FixedAssetsUpdateComponent } from './fixed-assets-update.component';
import { FixedAssetsDeletePopupComponent } from './fixed-assets-delete-dialog.component';
import { IFixedAssets } from 'app/shared/model/fixed-assets.model';

@Injectable({ providedIn: 'root' })
export class FixedAssetsResolve implements Resolve<IFixedAssets> {
    constructor(private service: FixedAssetsService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((fixedAssets: HttpResponse<FixedAssets>) => fixedAssets.body));
        }
        return of(new FixedAssets());
    }
}

export const fixedAssetsRoute: Routes = [
    {
        path: 'fixed-assets',
        component: FixedAssetsComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fixedAssetsTransferApp.fixedAssets.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'fixed-assets/:id/view',
        component: FixedAssetsDetailComponent,
        resolve: {
            fixedAssets: FixedAssetsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fixedAssetsTransferApp.fixedAssets.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'fixed-assets/new',
        component: FixedAssetsUpdateComponent,
        resolve: {
            fixedAssets: FixedAssetsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fixedAssetsTransferApp.fixedAssets.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'fixed-assets/:id/edit',
        component: FixedAssetsUpdateComponent,
        resolve: {
            fixedAssets: FixedAssetsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fixedAssetsTransferApp.fixedAssets.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const fixedAssetsPopupRoute: Routes = [
    {
        path: 'fixed-assets/:id/delete',
        component: FixedAssetsDeletePopupComponent,
        resolve: {
            fixedAssets: FixedAssetsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'fixedAssetsTransferApp.fixedAssets.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
