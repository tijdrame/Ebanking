import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EbankingSharedModule } from '../../shared';
import { EbankingAdminModule } from '../../admin/admin.module';
import {
    AbonneService,
    AbonnePopupService,
    AbonneComponent,
    AbonneDetailComponent,
    AbonneDialogComponent,
    AbonnePopupComponent,
    AbonneDeletePopupComponent,
    AbonneDeleteDialogComponent,
    abonneRoute,
    abonnePopupRoute,
    AbonneResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...abonneRoute,
    ...abonnePopupRoute,
];

@NgModule({
    imports: [
        EbankingSharedModule,
        EbankingAdminModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        AbonneComponent,
        AbonneDetailComponent,
        AbonneDialogComponent,
        AbonneDeleteDialogComponent,
        AbonnePopupComponent,
        AbonneDeletePopupComponent,
    ],
    entryComponents: [
        AbonneComponent,
        AbonneDialogComponent,
        AbonnePopupComponent,
        AbonneDeleteDialogComponent,
        AbonneDeletePopupComponent,
    ],
    providers: [
        AbonneService,
        AbonnePopupService,
        AbonneResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EbankingAbonneModule {}
