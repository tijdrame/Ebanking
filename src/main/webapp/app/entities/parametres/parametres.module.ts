import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EbankingSharedModule } from '../../shared';
import {
    ParametresService,
    ParametresPopupService,
    ParametresComponent,
    ParametresDetailComponent,
    ParametresDialogComponent,
    ParametresPopupComponent,
    ParametresDeletePopupComponent,
    ParametresDeleteDialogComponent,
    parametresRoute,
    parametresPopupRoute,
    ParametresResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...parametresRoute,
    ...parametresPopupRoute,
];

@NgModule({
    imports: [
        EbankingSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ParametresComponent,
        ParametresDetailComponent,
        ParametresDialogComponent,
        ParametresDeleteDialogComponent,
        ParametresPopupComponent,
        ParametresDeletePopupComponent,
    ],
    entryComponents: [
        ParametresComponent,
        ParametresDialogComponent,
        ParametresPopupComponent,
        ParametresDeleteDialogComponent,
        ParametresDeletePopupComponent,
    ],
    providers: [
        ParametresService,
        ParametresPopupService,
        ParametresResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EbankingParametresModule {}
