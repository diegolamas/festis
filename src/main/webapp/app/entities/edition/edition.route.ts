import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { EditionComponent } from './edition.component';
import { EditionDetailComponent } from './edition-detail.component';
import { EditionPopupComponent } from './edition-dialog.component';
import { EditionDeletePopupComponent } from './edition-delete-dialog.component';

export const editionRoute: Routes = [
    {
        path: 'edition',
        component: EditionComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'festisApp.edition.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'edition/:id',
        component: EditionDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'festisApp.edition.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const editionPopupRoute: Routes = [
    {
        path: 'edition-new',
        component: EditionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'festisApp.edition.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'edition/:id/edit',
        component: EditionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'festisApp.edition.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'edition/:id/delete',
        component: EditionDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'festisApp.edition.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
