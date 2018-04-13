import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { FestisFestivalModule } from './festival/festival.module';
import { FestisEditionModule } from './edition/edition.module';
import { FestisCommentModule } from './comment/comment.module';
import { FestisArtistModule } from './artist/artist.module';
import { FestisAnnouncementModule } from './announcement/announcement.module';
import { FestisFollowModule } from './follow/follow.module';
import { FestisUserExtraModule } from './user-extra/user-extra.module';
import { FestisAttendModule } from './attend/attend.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        FestisFestivalModule,
        FestisEditionModule,
        FestisCommentModule,
        FestisArtistModule,
        FestisAnnouncementModule,
        FestisFollowModule,
        FestisUserExtraModule,
        FestisAttendModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FestisEntityModule {}
