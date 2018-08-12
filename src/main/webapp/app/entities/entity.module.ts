import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { FixedAssetsTransferFixedAssetsModule } from './fixed-assets/fixed-assets.module';
import { FixedAssetsTransferTestingModule } from './testing/testing.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        FixedAssetsTransferFixedAssetsModule,
        FixedAssetsTransferTestingModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FixedAssetsTransferEntityModule {}