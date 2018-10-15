import { BaseEntity } from './../../shared';

export class Beneficiaire implements BaseEntity {
    constructor(
        public id?: number,
        public titulaire?: string,
        public numeroCompte?: string,
        public dateDemande?: any,
        public dateAcceptation?: any,
        public deleted?: boolean,
        public abonne?: BaseEntity,
        public statut?: BaseEntity,
        public banquesPartenaires?: BaseEntity,
    ) {
        this.deleted = false;
    }
}
