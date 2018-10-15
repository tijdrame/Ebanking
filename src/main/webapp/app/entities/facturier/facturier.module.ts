import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EbankingSharedModule } from '../../shared';
import {
    FacturierService,
    FacturierPopupService,
    FacturierComponent,
    FacturierDetailComponent,
    FacturierDialogComponent,
    FacturierPopupComponent,
    FacturierDeletePopupComponent,
    FacturierDeleteDialogComponent,
    facturierRoute,
    facturierPopupRoute,
    FacturierResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...facturierRoute,
    ...facturierPopupRoute,
];

@NgModule({
    imports: [
        EbankingSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        FacturierComponent,
        FacturierDetailComponent,
        FacturierDialogComponent,
        FacturierDeleteDialogComponent,
        FacturierPopupComponent,
        FacturierDeletePopupComponent,
    ],
    entryComponents: [
        FacturierComponent,
        FacturierDialogComponent,
        FacturierPopupComponent,
        FacturierDeleteDialogComponent,
        FacturierDeletePopupComponent,
    ],
    providers: [
        FacturierService,
        FacturierPopupService,
        FacturierResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EbankingFacturierModule {}
