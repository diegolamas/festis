import { BaseEntity } from './../../shared';

export class Artist implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public previewUrl?: string,
        public web?: string,
        public spotify?: string,
        public googlemusic?: string,
        public applemusic?: string,
        public facebook?: string,
        public twitter?: string,
        public instagram?: string,
        public youtube?: string,
        public imageUrl?: string,
        public popularity?: number,
        public followers?: BaseEntity[],
        public announcements?: BaseEntity[],
    ) {
    }
}
