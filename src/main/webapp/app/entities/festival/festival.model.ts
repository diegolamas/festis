import { BaseEntity } from './../../shared';

export class Festival implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public web?: string,
        public facebook?: string,
        public twitter?: string,
        public instagram?: string,
        public youtube?: string,
    ) {
    }
}
