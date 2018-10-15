import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EbankingSharedModule } from '../../shared';
import {
    TypeOppositionService,
    TypeOppositionPopupService,
    TypeOppositionComponent,
    TypeOppositionDetailComponent,
    TypeOppositionDialogComponent,
    TypeOppositionPopupComponent,
    TypeOppositionDeletePopupComponent,
    TypeOppositionDeleteDialogComponent,
    typeOppositionRoute,
    typeOppositionPopupRoute,
    TypeOppositionResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...typeOppositionRoute,
    ...typeOppositionPopupRoute,
];

@NgModule({
    imports: [
        EbankingSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        TypeOppositionComponent,
        TypeOppositionDetailComponent,
        TypeOppositionDialogComponent,
        TypeOppositionDeleteDialogComponent,
        TypeOppositionPopupComponent,
        TypeOppositionDeletePopupComponent,
    ],
    entryComponents: [
        TypeOppositionComponent,
        TypeOppositionDialogComponent,
        TypeOppositionPopupComponent,
        TypeOppositionDeleteDialogComponent,
        TypeOppositionDeletePopupComponent,
    ],
    providers: [
        TypeOppositionService,
        TypeOppositionPopupService,
        TypeOppositionResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EbankingTypeOppositionModule {}
