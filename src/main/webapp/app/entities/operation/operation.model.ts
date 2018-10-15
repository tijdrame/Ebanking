import { BaseEntity } from './../../shared';

export class Operation implements BaseEntity {
    constructor(
        public id?: number,
        public dateOperation?: any,
        public dateAcceptation?: any,
        public dateExecution?: any,
        public motif?: string,
        public numeroTransaction?: string,
        public deleted?: boolean,
        public operationType?: BaseEntity,
        public priseEnCharge?: BaseEntity,
        public compte?: BaseEntity,
        public operationsVirements?: BaseEntity[],
    ) {
        this.deleted = false;
    }
}
