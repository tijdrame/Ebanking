import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EbankingSharedModule } from '../../shared';
import {
    BeneficiaireService,
    BeneficiairePopupService,
    BeneficiaireComponent,
    BeneficiaireDetailComponent,
    BeneficiaireDialogComponent,
    BeneficiairePopupComponent,
    BeneficiaireDeletePopupComponent,
    BeneficiaireDeleteDialogComponent,
    beneficiaireRoute,
    beneficiairePopupRoute,
    BeneficiaireResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...beneficiaireRoute,
    ...beneficiairePopupRoute,
];

@NgModule({
    imports: [
        EbankingSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        BeneficiaireComponent,
        BeneficiaireDetailComponent,
        BeneficiaireDialogComponent,
        BeneficiaireDeleteDialogComponent,
        BeneficiairePopupComponent,
        BeneficiaireDeletePopupComponent,
    ],
    entryComponents: [
        BeneficiaireComponent,
        BeneficiaireDialogComponent,
        BeneficiairePopupComponent,
        BeneficiaireDeleteDialogComponent,
        BeneficiaireDeletePopupComponent,
    ],
    providers: [
        BeneficiaireService,
        BeneficiairePopupService,
        BeneficiaireResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EbankingBeneficiaireModule {}
