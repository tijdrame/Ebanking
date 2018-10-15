import { BaseEntity } from './../../shared';

export class Agence implements BaseEntity {
    constructor(
        public id?: number,
        public nom?: string,
        public email?: string,
        public telephone?: string,
        public adresse?: string,
        public longitude?: number,
        public latitude?: number,
        public etatGab?: boolean,
        public deleted?: boolean,
    ) {
        this.etatGab = false;
        this.deleted = false;
    }
}
