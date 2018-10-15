import { BaseEntity } from './../../shared';

export class PaiementFacture implements BaseEntity {
    constructor(
        public id?: number,
        public numeroFacture?: string,
        public montant?: number,
        public dateDebut?: any,
        public dateFin?: any,
        public dateAcceptation?: any,
        public dateDemande?: any,
        public estTelecharge?: boolean,
        public deleted?: boolean,
        public devise?: BaseEntity,
        public compte?: BaseEntity,
        public facturier?: BaseEntity,
        public statut?: BaseEntity,
    ) {
        this.estTelecharge = false;
        this.deleted = false;
    }
}
