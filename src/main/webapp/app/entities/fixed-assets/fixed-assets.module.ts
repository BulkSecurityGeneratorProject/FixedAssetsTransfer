import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FixedAssetsTransferSharedModule } from 'app/shared';
import {
    FixedAssetsComponent,
    FixedAssetsDetailComponent,
    FixedAssetsUpdateComponent,
    FixedAssetsDeletePopupComponent,
    FixedAssetsDeleteDialogComponent,
    fixedAssetsRoute,
    fixedAssetsPopupRoute
} from './';

const ENTITY_STATES = [...fixedAssetsRoute, ...fixedAssetsPopupRoute];

@NgModule({
    imports: [FixedAssetsTransferSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        FixedAssetsComponent,
        FixedAssetsDetailComponent,
        FixedAssetsUpdateComponent,
        FixedAssetsDeleteDialogComponent,
        FixedAssetsDeletePopupComponent
    ],
    entryComponents: [FixedAssetsComponent, FixedAssetsUpdateComponent, FixedAssetsDeleteDialogComponent, FixedAssetsDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FixedAssetsTransferFixedAssetsModule {}
