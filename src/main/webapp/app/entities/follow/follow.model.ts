import { BaseEntity } from './../../shared';

export class Follow implements BaseEntity {
    constructor(
        public id?: number,
        public userLogin?: string,
        public userId?: number,
        public artistName?: string,
        public artistId?: number,
    ) {
    }
}
