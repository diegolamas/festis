import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FestisSharedModule } from '../../shared';
import {
    EditionService,
    EditionPopupService,
    EditionComponent,
    EditionDetailComponent,
    EditionDialogComponent,
    EditionPopupComponent,
    EditionDeletePopupComponent,
    EditionDeleteDialogComponent,
    editionRoute,
    editionPopupRoute,
} from './';

const ENTITY_STATES = [
    ...editionRoute,
    ...editionPopupRoute,
];

@NgModule({
    imports: [
        FestisSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        EditionComponent,
        EditionDetailComponent,
        EditionDialogComponent,
        EditionDeleteDialogComponent,
        EditionPopupComponent,
        EditionDeletePopupComponent,
    ],
    entryComponents: [
        EditionComponent,
        EditionDialogComponent,
        EditionPopupComponent,
        EditionDeleteDialogComponent,
        EditionDeletePopupComponent,
    ],
    providers: [
        EditionService,
        EditionPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FestisEditionModule {}
