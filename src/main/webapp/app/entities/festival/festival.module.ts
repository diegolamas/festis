import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FestisSharedModule } from '../../shared';
import {
    FestivalService,
    FestivalPopupService,
    FestivalComponent,
    FestivalDetailComponent,
    FestivalDialogComponent,
    FestivalPopupComponent,
    FestivalDeletePopupComponent,
    FestivalDeleteDialogComponent,
    festivalRoute,
    festivalPopupRoute,
} from './';

const ENTITY_STATES = [
    ...festivalRoute,
    ...festivalPopupRoute,
];

@NgModule({
    imports: [
        FestisSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        FestivalComponent,
        FestivalDetailComponent,
        FestivalDialogComponent,
        FestivalDeleteDialogComponent,
        FestivalPopupComponent,
        FestivalDeletePopupComponent,
    ],
    entryComponents: [
        FestivalComponent,
        FestivalDialogComponent,
        FestivalPopupComponent,
        FestivalDeleteDialogComponent,
        FestivalDeletePopupComponent,
    ],
    providers: [
        FestivalService,
        FestivalPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FestisFestivalModule {}
