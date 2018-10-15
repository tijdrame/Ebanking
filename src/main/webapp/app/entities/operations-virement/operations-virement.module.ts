import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EbankingSharedModule } from '../../shared';
import {
    OperationsVirementService,
    OperationsVirementPopupService,
    OperationsVirementComponent,
    OperationsVirementDetailComponent,
    OperationsVirementDialogComponent,
    OperationsVirementPopupComponent,
    OperationsVirementDeletePopupComponent,
    OperationsVirementDeleteDialogComponent,
    operationsVirementRoute,
    operationsVirementPopupRoute,
    OperationsVirementResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...operationsVirementRoute,
    ...operationsVirementPopupRoute,
];

@NgModule({
    imports: [
        EbankingSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        OperationsVirementComponent,
        OperationsVirementDetailComponent,
        OperationsVirementDialogComponent,
        OperationsVirementDeleteDialogComponent,
        OperationsVirementPopupComponent,
        OperationsVirementDeletePopupComponent,
    ],
    entryComponents: [
        OperationsVirementComponent,
        OperationsVirementDialogComponent,
        OperationsVirementPopupComponent,
        OperationsVirementDeleteDialogComponent,
        OperationsVirementDeletePopupComponent,
    ],
    providers: [
        OperationsVirementService,
        OperationsVirementPopupService,
        OperationsVirementResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EbankingOperationsVirementModule {}
