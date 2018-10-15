import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EbankingSharedModule } from '../../shared';
import {
    DeviseService,
    DevisePopupService,
    DeviseComponent,
    DeviseDetailComponent,
    DeviseDialogComponent,
    DevisePopupComponent,
    DeviseDeletePopupComponent,
    DeviseDeleteDialogComponent,
    deviseRoute,
    devisePopupRoute,
    DeviseResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...deviseRoute,
    ...devisePopupRoute,
];

@NgModule({
    imports: [
        EbankingSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        DeviseComponent,
        DeviseDetailComponent,
        DeviseDialogComponent,
        DeviseDeleteDialogComponent,
        DevisePopupComponent,
        DeviseDeletePopupComponent,
    ],
    entryComponents: [
        DeviseComponent,
        DeviseDialogComponent,
        DevisePopupComponent,
        DeviseDeleteDialogComponent,
        DeviseDeletePopupComponent,
    ],
    providers: [
        DeviseService,
        DevisePopupService,
        DeviseResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EbankingDeviseModule {}
