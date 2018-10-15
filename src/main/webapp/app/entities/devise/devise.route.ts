import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { DeviseComponent } from './devise.component';
import { DeviseDetailComponent } from './devise-detail.component';
import { DevisePopupComponent } from './devise-dialog.component';
import { DeviseDeletePopupComponent } from './devise-delete-dialog.component';

@Injectable()
export class DeviseResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const deviseRoute: Routes = [
    {
        path: 'devise',
        component: DeviseComponent,
        resolve: {
            'pagingParams': DeviseResolvePagingParams
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ebankingApp.devise.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'devise/:id',
        component: DeviseDetailComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ebankingApp.devise.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const devisePopupRoute: Routes = [
    {
        path: 'devise-new',
        component: DevisePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ebankingApp.devise.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'devise/:id/edit',
        component: DevisePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ebankingApp.devise.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'devise/:id/delete',
        component: DeviseDeletePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ebankingApp.devise.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
