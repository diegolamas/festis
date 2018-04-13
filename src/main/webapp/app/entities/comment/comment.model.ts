import { BaseEntity } from './../../shared';

export class Comment implements BaseEntity {
    constructor(
        public id?: number,
        public text?: string,
        public editionId?: number,
    ) {
    }
}
