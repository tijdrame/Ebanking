import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EbankingSharedModule } from '../../shared';
import {
    NbreFeuillesChequierService,
    NbreFeuillesChequierPopupService,
    NbreFeuillesChequierComponent,
    NbreFeuillesChequierDetailComponent,
    NbreFeuillesChequierDialogComponent,
    NbreFeuillesChequierPopupComponent,
    NbreFeuillesChequierDeletePopupComponent,
    NbreFeuillesChequierDeleteDialogComponent,
    nbreFeuillesChequierRoute,
    nbreFeuillesChequierPopupRoute,
    NbreFeuillesChequierResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...nbreFeuillesChequierRoute,
    ...nbreFeuillesChequierPopupRoute,
];

@NgModule({
    imports: [
        EbankingSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        NbreFeuillesChequierComponent,
        NbreFeuillesChequierDetailComponent,
        NbreFeuillesChequierDialogComponent,
        NbreFeuillesChequierDeleteDialogComponent,
        NbreFeuillesChequierPopupComponent,
        NbreFeuillesChequierDeletePopupComponent,
    ],
    entryComponents: [
        NbreFeuillesChequierComponent,
        NbreFeuillesChequierDialogComponent,
        NbreFeuillesChequierPopupComponent,
        NbreFeuillesChequierDeleteDialogComponent,
        NbreFeuillesChequierDeletePopupComponent,
    ],
    providers: [
        NbreFeuillesChequierService,
        NbreFeuillesChequierPopupService,
        NbreFeuillesChequierResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EbankingNbreFeuillesChequierModule {}
