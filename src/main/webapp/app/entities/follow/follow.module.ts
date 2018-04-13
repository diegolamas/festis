import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FestisSharedModule } from '../../shared';
import { FestisAdminModule } from '../../admin/admin.module';
import {
    FollowService,
    FollowPopupService,
    FollowComponent,
    FollowDetailComponent,
    FollowDialogComponent,
    FollowPopupComponent,
    FollowDeletePopupComponent,
    FollowDeleteDialogComponent,
    followRoute,
    followPopupRoute,
} from './';

const ENTITY_STATES = [
    ...followRoute,
    ...followPopupRoute,
];

@NgModule({
    imports: [
        FestisSharedModule,
        FestisAdminModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        FollowComponent,
        FollowDetailComponent,
        FollowDialogComponent,
        FollowDeleteDialogComponent,
        FollowPopupComponent,
        FollowDeletePopupComponent,
    ],
    entryComponents: [
        FollowComponent,
        FollowDialogComponent,
        FollowPopupComponent,
        FollowDeleteDialogComponent,
        FollowDeletePopupComponent,
    ],
    providers: [
        FollowService,
        FollowPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FestisFollowModule {}
