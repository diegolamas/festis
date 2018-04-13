import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { AnnouncementComponent } from './announcement.component';
import { AnnouncementDetailComponent } from './announcement-detail.component';
import { AnnouncementPopupComponent } from './announcement-dialog.component';
import { AnnouncementDeletePopupComponent } from './announcement-delete-dialog.component';

export const announcementRoute: Routes = [
    {
        path: 'announcement',
        component: AnnouncementComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'festisApp.announcement.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'announcement/:id',
        component: AnnouncementDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'festisApp.announcement.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const announcementPopupRoute: Routes = [
    {
        path: 'announcement-new',
        component: AnnouncementPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'festisApp.announcement.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'announcement/:id/edit',
        component: AnnouncementPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'festisApp.announcement.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'announcement/:id/delete',
        component: AnnouncementDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'festisApp.announcement.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
