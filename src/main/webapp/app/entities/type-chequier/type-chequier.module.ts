import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EbankingSharedModule } from '../../shared';
import {
    TypeChequierService,
    TypeChequierPopupService,
    TypeChequierComponent,
    TypeChequierDetailComponent,
    TypeChequierDialogComponent,
    TypeChequierPopupComponent,
    TypeChequierDeletePopupComponent,
    TypeChequierDeleteDialogComponent,
    typeChequierRoute,
    typeChequierPopupRoute,
    TypeChequierResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...typeChequierRoute,
    ...typeChequierPopupRoute,
];

@NgModule({
    imports: [
        EbankingSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        TypeChequierComponent,
        TypeChequierDetailComponent,
        TypeChequierDialogComponent,
        TypeChequierDeleteDialogComponent,
        TypeChequierPopupComponent,
        TypeChequierDeletePopupComponent,
    ],
    entryComponents: [
        TypeChequierComponent,
        TypeChequierDialogComponent,
        TypeChequierPopupComponent,
        TypeChequierDeleteDialogComponent,
        TypeChequierDeletePopupComponent,
    ],
    providers: [
        TypeChequierService,
        TypeChequierPopupService,
        TypeChequierResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EbankingTypeChequierModule {}
