import { BaseEntity } from './../../shared';

export class Compte implements BaseEntity {
    constructor(
        public id?: number,
        public numero?: string,
        public numeroComplet?: string,
        public libelle?: string,
        public solde?: number,
        public deleted?: boolean,
        public sens?: string,
        public abonne?: BaseEntity,
        public devise?: BaseEntity,
        public typeCompte?: BaseEntity,
    ) {
        this.deleted = false;
    }
}
