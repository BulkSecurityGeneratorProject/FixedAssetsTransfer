import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FixedAssetsTransferSharedModule } from 'app/shared';
import {
    TestingComponent,
    TestingDetailComponent,
    TestingUpdateComponent,
    TestingDeletePopupComponent,
    TestingDeleteDialogComponent,
    testingRoute,
    testingPopupRoute
} from './';

const ENTITY_STATES = [...testingRoute, ...testingPopupRoute];

@NgModule({
    imports: [FixedAssetsTransferSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TestingComponent,
        TestingDetailComponent,
        TestingUpdateComponent,
        TestingDeleteDialogComponent,
        TestingDeletePopupComponent
    ],
    entryComponents: [TestingComponent, TestingUpdateComponent, TestingDeleteDialogComponent, TestingDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FixedAssetsTransferTestingModule {}
