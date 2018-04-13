import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { AttendComponent } from './attend.component';
import { AttendDetailComponent } from './attend-detail.component';
import { AttendPopupComponent } from './attend-dialog.component';
import { AttendDeletePopupComponent } from './attend-delete-dialog.component';

export const attendRoute: Routes = [
    {
        path: 'attend',
        component: AttendComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'festisApp.attend.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'attend/:id',
        component: AttendDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'festisApp.attend.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const attendPopupRoute: Routes = [
    {
        path: 'attend-new',
        component: AttendPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'festisApp.attend.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'attend/:id/edit',
        component: AttendPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'festisApp.attend.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'attend/:id/delete',
        component: AttendDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'festisApp.attend.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
