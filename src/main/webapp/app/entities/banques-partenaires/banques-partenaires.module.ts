import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EbankingSharedModule } from '../../shared';
import {
    BanquesPartenairesService,
    BanquesPartenairesPopupService,
    BanquesPartenairesComponent,
    BanquesPartenairesDetailComponent,
    BanquesPartenairesDialogComponent,
    BanquesPartenairesPopupComponent,
    BanquesPartenairesDeletePopupComponent,
    BanquesPartenairesDeleteDialogComponent,
    banquesPartenairesRoute,
    banquesPartenairesPopupRoute,
    BanquesPartenairesResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...banquesPartenairesRoute,
    ...banquesPartenairesPopupRoute,
];

@NgModule({
    imports: [
        EbankingSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        BanquesPartenairesComponent,
        BanquesPartenairesDetailComponent,
        BanquesPartenairesDialogComponent,
        BanquesPartenairesDeleteDialogComponent,
        BanquesPartenairesPopupComponent,
        BanquesPartenairesDeletePopupComponent,
    ],
    entryComponents: [
        BanquesPartenairesComponent,
        BanquesPartenairesDialogComponent,
        BanquesPartenairesPopupComponent,
        BanquesPartenairesDeleteDialogComponent,
        BanquesPartenairesDeletePopupComponent,
    ],
    providers: [
        BanquesPartenairesService,
        BanquesPartenairesPopupService,
        BanquesPartenairesResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EbankingBanquesPartenairesModule {}
