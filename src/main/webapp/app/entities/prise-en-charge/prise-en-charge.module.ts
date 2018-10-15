import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EbankingSharedModule } from '../../shared';
import {
    PriseEnChargeService,
    PriseEnChargePopupService,
    PriseEnChargeComponent,
    PriseEnChargeDetailComponent,
    PriseEnChargeDialogComponent,
    PriseEnChargePopupComponent,
    PriseEnChargeDeletePopupComponent,
    PriseEnChargeDeleteDialogComponent,
    priseEnChargeRoute,
    priseEnChargePopupRoute,
    PriseEnChargeResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...priseEnChargeRoute,
    ...priseEnChargePopupRoute,
];

@NgModule({
    imports: [
        EbankingSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        PriseEnChargeComponent,
        PriseEnChargeDetailComponent,
        PriseEnChargeDialogComponent,
        PriseEnChargeDeleteDialogComponent,
        PriseEnChargePopupComponent,
        PriseEnChargeDeletePopupComponent,
    ],
    entryComponents: [
        PriseEnChargeComponent,
        PriseEnChargeDialogComponent,
        PriseEnChargePopupComponent,
        PriseEnChargeDeleteDialogComponent,
        PriseEnChargeDeletePopupComponent,
    ],
    providers: [
        PriseEnChargeService,
        PriseEnChargePopupService,
        PriseEnChargeResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EbankingPriseEnChargeModule {}
