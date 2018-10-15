import { BaseEntity, User } from './../../shared';

export class LogEvenement implements BaseEntity {
    constructor(
        public id?: number,
        public codeObjet?: number,
        public entityName?: string,
        public eventName?: string,
        public adresseMac?: string,
        public adresseIP?: string,
        public dateCreated?: any,
        public userCreated?: User,
    ) {
    }
}
