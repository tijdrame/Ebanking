import { BaseEntity } from './../../shared';

export class OperationsVirement implements BaseEntity {
    constructor(
        public id?: number,
        public montant?: number,
        public code?: string,
        public deleted?: boolean,
        public beneficiaire?: BaseEntity,
        public devise?: BaseEntity,
        public statut?: BaseEntity,
        public operation?: BaseEntity,
    ) {
        this.deleted = false;
    }
}
