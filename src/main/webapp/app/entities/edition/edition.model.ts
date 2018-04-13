import { BaseEntity } from './../../shared';

export class Edition implements BaseEntity {
    constructor(
        public id?: number,
        public location?: string,
        public startDate?: any,
        public endDate?: any,
        public hasCamping?: boolean,
        public price?: number,
        public coverContentType?: string,
        public cover?: any,
        public posterContentType?: string,
        public poster?: any,
        public attendants?: BaseEntity[],
        public announcements?: BaseEntity[],
        public comments?: BaseEntity[],
        public festivalName?: string,
        public festivalId?: number,
    ) {
        this.hasCamping = false;
    }
}
