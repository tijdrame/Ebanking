import { BaseEntity } from './../../shared';

export class NbreFeuillesChequier implements BaseEntity {
    constructor(
        public id?: number,
        public nbFeuilles?: number,
        public libelle?: string,
        public deleted?: boolean,
    ) {
        this.deleted = false;
    }
}
