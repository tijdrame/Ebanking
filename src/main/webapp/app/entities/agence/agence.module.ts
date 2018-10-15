import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EbankingSharedModule } from '../../shared';
import {
    AgenceService,
    AgencePopupService,
    AgenceComponent,
    AgenceDetailComponent,
    AgenceDialogComponent,
    AgencePopupComponent,
    AgenceDeletePopupComponent,
    AgenceDeleteDialogComponent,
    agenceRoute,
    agencePopupRoute,
    AgenceResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...agenceRoute,
    ...agencePopupRoute,
];

@NgModule({
    imports: [
        EbankingSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        AgenceComponent,
        AgenceDetailComponent,
        AgenceDialogComponent,
        AgenceDeleteDialogComponent,
        AgencePopupComponent,
        AgenceDeletePopupComponent,
    ],
    entryComponents: [
        AgenceComponent,
        AgenceDialogComponent,
        AgencePopupComponent,
        AgenceDeleteDialogComponent,
        AgenceDeletePopupComponent,
    ],
    providers: [
        AgenceService,
        AgencePopupService,
        AgenceResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EbankingAgenceModule {}
