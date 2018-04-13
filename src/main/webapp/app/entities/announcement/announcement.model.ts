import { BaseEntity } from './../../shared';

export class Announcement implements BaseEntity {
    constructor(
        public id?: number,
        public text?: string,
        public imageContentType?: string,
        public image?: any,
        public artists?: BaseEntity[],
        public editionId?: number,
    ) {
    }
}
