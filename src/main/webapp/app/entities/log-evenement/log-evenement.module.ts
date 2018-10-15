import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EbankingSharedModule } from '../../shared';
import { EbankingAdminModule } from '../../admin/admin.module';
import {
    LogEvenementService,
    LogEvenementPopupService,
    LogEvenementComponent,
    LogEvenementDetailComponent,
    LogEvenementDialogComponent,
    LogEvenementPopupComponent,
    LogEvenementDeletePopupComponent,
    LogEvenementDeleteDialogComponent,
    logEvenementRoute,
    logEvenementPopupRoute,
    LogEvenementResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...logEvenementRoute,
    ...logEvenementPopupRoute,
];

@NgModule({
    imports: [
        EbankingSharedModule,
        EbankingAdminModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        LogEvenementComponent,
        LogEvenementDetailComponent,
        LogEvenementDialogComponent,
        LogEvenementDeleteDialogComponent,
        LogEvenementPopupComponent,
        LogEvenementDeletePopupComponent,
    ],
    entryComponents: [
        LogEvenementComponent,
        LogEvenementDialogComponent,
        LogEvenementPopupComponent,
        LogEvenementDeleteDialogComponent,
        LogEvenementDeletePopupComponent,
    ],
    providers: [
        LogEvenementService,
        LogEvenementPopupService,
        LogEvenementResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EbankingLogEvenementModule {}
