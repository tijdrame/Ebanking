import { BaseEntity } from './../../shared';

export class Parametres implements BaseEntity {
    constructor(
        public id?: number,
        public cle?: string,
        public valeur?: string,
        public deleted?: boolean,
    ) {
        this.deleted = false;
    }
}
