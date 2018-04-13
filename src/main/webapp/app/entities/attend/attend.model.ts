import { BaseEntity } from './../../shared';

export class Attend implements BaseEntity {
    constructor(
        public id?: number,
        public userLogin?: string,
        public userId?: number,
        public editionId?: number,
    ) {
    }
}
