import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EbankingSharedModule } from '../../shared';
import {
    PaiementFactureService,
    PaiementFacturePopupService,
    PaiementFactureComponent,
    PaiementFactureDetailComponent,
    PaiementFactureDialogComponent,
    PaiementFacturePopupComponent,
    PaiementFactureDeletePopupComponent,
    PaiementFactureDeleteDialogComponent,
    paiementFactureRoute,
    paiementFacturePopupRoute,
    PaiementFactureResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...paiementFactureRoute,
    ...paiementFacturePopupRoute,
];

@NgModule({
    imports: [
        EbankingSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        PaiementFactureComponent,
        PaiementFactureDetailComponent,
        PaiementFactureDialogComponent,
        PaiementFactureDeleteDialogComponent,
        PaiementFacturePopupComponent,
        PaiementFactureDeletePopupComponent,
    ],
    entryComponents: [
        PaiementFactureComponent,
        PaiementFactureDialogComponent,
        PaiementFacturePopupComponent,
        PaiementFactureDeleteDialogComponent,
        PaiementFactureDeletePopupComponent,
    ],
    providers: [
        PaiementFactureService,
        PaiementFacturePopupService,
        PaiementFactureResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EbankingPaiementFactureModule {}
