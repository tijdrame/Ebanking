import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EbankingSharedModule } from '../../shared';
import {
    TypeCarteService,
    TypeCartePopupService,
    TypeCarteComponent,
    TypeCarteDetailComponent,
    TypeCarteDialogComponent,
    TypeCartePopupComponent,
    TypeCarteDeletePopupComponent,
    TypeCarteDeleteDialogComponent,
    typeCarteRoute,
    typeCartePopupRoute,
    TypeCarteResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...typeCarteRoute,
    ...typeCartePopupRoute,
];

@NgModule({
    imports: [
        EbankingSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        TypeCarteComponent,
        TypeCarteDetailComponent,
        TypeCarteDialogComponent,
        TypeCarteDeleteDialogComponent,
        TypeCartePopupComponent,
        TypeCarteDeletePopupComponent,
    ],
    entryComponents: [
        TypeCarteComponent,
        TypeCarteDialogComponent,
        TypeCartePopupComponent,
        TypeCarteDeleteDialogComponent,
        TypeCarteDeletePopupComponent,
    ],
    providers: [
        TypeCarteService,
        TypeCartePopupService,
        TypeCarteResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EbankingTypeCarteModule {}
