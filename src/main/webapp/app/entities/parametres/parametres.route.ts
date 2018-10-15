import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { ParametresComponent } from './parametres.component';
import { ParametresDetailComponent } from './parametres-detail.component';
import { ParametresPopupComponent } from './parametres-dialog.component';
import { ParametresDeletePopupComponent } from './parametres-delete-dialog.component';

@Injectable()
export class ParametresResolvePagingParams implements Resolve<any> {

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

export const parametresRoute: Routes = [
    {
        path: 'parametres',
        component: ParametresComponent,
        resolve: {
            'pagingParams': ParametresResolvePagingParams
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ebankingApp.parametres.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'parametres/:id',
        component: ParametresDetailComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ebankingApp.parametres.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const parametresPopupRoute: Routes = [
    {
        path: 'parametres-new',
        component: ParametresPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ebankingApp.parametres.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'parametres/:id/edit',
        component: ParametresPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ebankingApp.parametres.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'parametres/:id/delete',
        component: ParametresDeletePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ebankingApp.parametres.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
