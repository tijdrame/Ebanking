import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EbankingSharedModule } from '../../shared';
import {
    TypeAbonneService,
    TypeAbonnePopupService,
    TypeAbonneComponent,
    TypeAbonneDetailComponent,
    TypeAbonneDialogComponent,
    TypeAbonnePopupComponent,
    TypeAbonneDeletePopupComponent,
    TypeAbonneDeleteDialogComponent,
    typeAbonneRoute,
    typeAbonnePopupRoute,
    TypeAbonneResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...typeAbonneRoute,
    ...typeAbonnePopupRoute,
];

@NgModule({
    imports: [
        EbankingSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        TypeAbonneComponent,
        TypeAbonneDetailComponent,
        TypeAbonneDialogComponent,
        TypeAbonneDeleteDialogComponent,
        TypeAbonnePopupComponent,
        TypeAbonneDeletePopupComponent,
    ],
    entryComponents: [
        TypeAbonneComponent,
        TypeAbonneDialogComponent,
        TypeAbonnePopupComponent,
        TypeAbonneDeleteDialogComponent,
        TypeAbonneDeletePopupComponent,
    ],
    providers: [
        TypeAbonneService,
        TypeAbonnePopupService,
        TypeAbonneResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EbankingTypeAbonneModule {}
