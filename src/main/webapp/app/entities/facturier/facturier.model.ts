import { BaseEntity } from './../../shared';

export class Facturier implements BaseEntity {
    constructor(
        public id?: number,
        public libelle?: string,
        public code?: string,
        public numeroCompte?: string,
        public deleted?: boolean,
    ) {
        this.deleted = false;
    }
}
