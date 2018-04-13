import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { ArtistComponent } from './artist.component';
import { ArtistDetailComponent } from './artist-detail.component';
import { ArtistPopupComponent } from './artist-dialog.component';
import { ArtistDeletePopupComponent } from './artist-delete-dialog.component';

export const artistRoute: Routes = [
    {
        path: 'artist',
        component: ArtistComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'festisApp.artist.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'artist/:id',
        component: ArtistDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'festisApp.artist.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const artistPopupRoute: Routes = [
    {
        path: 'artist-new',
        component: ArtistPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'festisApp.artist.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'artist/:id/edit',
        component: ArtistPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'festisApp.artist.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'artist/:id/delete',
        component: ArtistDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'festisApp.artist.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
