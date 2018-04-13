import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { FollowComponent } from './follow.component';
import { FollowDetailComponent } from './follow-detail.component';
import { FollowPopupComponent } from './follow-dialog.component';
import { FollowDeletePopupComponent } from './follow-delete-dialog.component';

export const followRoute: Routes = [
    {
        path: 'follow',
        component: FollowComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'festisApp.follow.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'follow/:id',
        component: FollowDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'festisApp.follow.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const followPopupRoute: Routes = [
    {
        path: 'follow-new',
        component: FollowPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'festisApp.follow.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'follow/:id/edit',
        component: FollowPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'festisApp.follow.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'follow/:id/delete',
        component: FollowDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'festisApp.follow.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
