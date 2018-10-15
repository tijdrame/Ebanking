import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EbankingSharedModule } from '../../shared';
import {
    TypeCompteService,
    TypeComptePopupService,
    TypeCompteComponent,
    TypeCompteDetailComponent,
    TypeCompteDialogComponent,
    TypeComptePopupComponent,
    TypeCompteDeletePopupComponent,
    TypeCompteDeleteDialogComponent,
    typeCompteRoute,
    typeComptePopupRoute,
    TypeCompteResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...typeCompteRoute,
    ...typeComptePopupRoute,
];

@NgModule({
    imports: [
        EbankingSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        TypeCompteComponent,
        TypeCompteDetailComponent,
        TypeCompteDialogComponent,
        TypeCompteDeleteDialogComponent,
        TypeComptePopupComponent,
        TypeCompteDeletePopupComponent,
    ],
    entryComponents: [
        TypeCompteComponent,
        TypeCompteDialogComponent,
        TypeComptePopupComponent,
        TypeCompteDeleteDialogComponent,
        TypeCompteDeletePopupComponent,
    ],
    providers: [
        TypeCompteService,
        TypeComptePopupService,
        TypeCompteResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EbankingTypeCompteModule {}
