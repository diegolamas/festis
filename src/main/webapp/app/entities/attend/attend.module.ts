import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FestisSharedModule } from '../../shared';
import { FestisAdminModule } from '../../admin/admin.module';
import {
    AttendService,
    AttendPopupService,
    AttendComponent,
    AttendDetailComponent,
    AttendDialogComponent,
    AttendPopupComponent,
    AttendDeletePopupComponent,
    AttendDeleteDialogComponent,
    attendRoute,
    attendPopupRoute,
} from './';

const ENTITY_STATES = [
    ...attendRoute,
    ...attendPopupRoute,
];

@NgModule({
    imports: [
        FestisSharedModule,
        FestisAdminModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        AttendComponent,
        AttendDetailComponent,
        AttendDialogComponent,
        AttendDeleteDialogComponent,
        AttendPopupComponent,
        AttendDeletePopupComponent,
    ],
    entryComponents: [
        AttendComponent,
        AttendDialogComponent,
        AttendPopupComponent,
        AttendDeleteDialogComponent,
        AttendDeletePopupComponent,
    ],
    providers: [
        AttendService,
        AttendPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FestisAttendModule {}
