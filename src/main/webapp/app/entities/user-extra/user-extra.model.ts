import { BaseEntity } from './../../shared';

export class UserExtra implements BaseEntity {
    constructor(
        public id?: number,
        public avatarContentType?: string,
        public avatar?: any,
        public userLogin?: string,
        public userId?: number,
    ) {
    }
}
