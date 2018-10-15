import { BaseEntity } from './../../shared';

export class Devise implements BaseEntity {
    constructor(
        public id?: number,
        public libelle?: string,
        public code?: string,
        public achatBb?: number,
        public achatTc?: number,
        public achatTr?: number,
        public venteBb?: number,
        public venteTc?: number,
        public venteTr?: number,
        public dateMaj?: any,
        public deleted?: boolean,
    ) {
        this.deleted = false;
    }
}
