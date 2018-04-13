import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { FestivalComponent } from './festival.component';
import { FestivalDetailComponent } from './festival-detail.component';
import { FestivalPopupComponent } from './festival-dialog.component';
import { FestivalDeletePopupComponent } from './festival-delete-dialog.component';

export const festivalRoute: Routes = [
    {
        path: 'festival',
        component: FestivalComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'festisApp.festival.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'festival/:id',
        component: FestivalDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'festisApp.festival.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const festivalPopupRoute: Routes = [
    {
        path: 'festival-new',
        component: FestivalPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'festisApp.festival.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'festival/:id/edit',
        component: FestivalPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'festisApp.festival.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'festival/:id/delete',
        component: FestivalDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'festisApp.festival.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
