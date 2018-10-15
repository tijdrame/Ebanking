import { BaseEntity, User } from './../../shared';

export class Abonne implements BaseEntity {
    constructor(
        public id?: number,
        public telephone?: string,
        public agence?: BaseEntity,
        public gestionnaire?: User,
        public user?: User,
    ) {
    }
}
