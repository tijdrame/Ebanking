import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { EbankingTypeAbonneModule } from './type-abonne/type-abonne.module';
import { EbankingAgenceModule } from './agence/agence.module';
import { EbankingTypeOppositionModule } from './type-opposition/type-opposition.module';
import { EbankingTypeCompteModule } from './type-compte/type-compte.module';
import { EbankingTypeChequierModule } from './type-chequier/type-chequier.module';
import { EbankingTypeCarteModule } from './type-carte/type-carte.module';
import { EbankingStatutModule } from './statut/statut.module';
import { EbankingPriseEnChargeModule } from './prise-en-charge/prise-en-charge.module';
import { EbankingParametresModule } from './parametres/parametres.module';
import { EbankingOperationTypeModule } from './operation-type/operation-type.module';
import { EbankingNbreFeuillesChequierModule } from './nbre-feuilles-chequier/nbre-feuilles-chequier.module';
import { EbankingFacturierModule } from './facturier/facturier.module';
import { EbankingDeviseModule } from './devise/devise.module';
import { EbankingBanquesPartenairesModule } from './banques-partenaires/banques-partenaires.module';
import { EbankingAbonneModule } from './abonne/abonne.module';
import { EbankingLogEvenementModule } from './log-evenement/log-evenement.module';
import { EbankingCompteModule } from './compte/compte.module';
import { EbankingPaiementFactureModule } from './paiement-facture/paiement-facture.module';
import { EbankingBeneficiaireModule } from './beneficiaire/beneficiaire.module';
import { EbankingOperationModule } from './operation/operation.module';
import { EbankingOperationsVirementModule } from './operations-virement/operations-virement.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        EbankingTypeAbonneModule,
        EbankingAgenceModule,
        EbankingTypeOppositionModule,
        EbankingTypeCompteModule,
        EbankingTypeChequierModule,
        EbankingTypeCarteModule,
        EbankingStatutModule,
        EbankingPriseEnChargeModule,
        EbankingParametresModule,
        EbankingOperationTypeModule,
        EbankingNbreFeuillesChequierModule,
        EbankingFacturierModule,
        EbankingDeviseModule,
        EbankingBanquesPartenairesModule,
        EbankingAbonneModule,
        EbankingLogEvenementModule,
        EbankingCompteModule,
        EbankingPaiementFactureModule,
        EbankingBeneficiaireModule,
        EbankingOperationModule,
        EbankingOperationsVirementModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EbankingEntityModule {}
