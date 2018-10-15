import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { AbonneComponent } from './abonne.component';
import { AbonneDetailComponent } from './abonne-detail.component';
import { AbonnePopupComponent } from './abonne-dialog.component';
import { AbonneDeletePopupComponent } from './abonne-delete-dialog.component';

@Injectable()
export class AbonneResolvePagingParams implements Resolve<any> {

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

export const abonneRoute: Routes = [
    {
        path: 'abonne',
        component: AbonneComponent,
        resolve: {
            'pagingParams': AbonneResolvePagingParams
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_GESTIONNAIRE'],
            pageTitle: 'ebankingApp.abonne.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'abonne/:id',
        component: AbonneDetailComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_GESTIONNAIRE'],
            pageTitle: 'ebankingApp.abonne.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const abonnePopupRoute: Routes = [
    {
        path: 'abonne-new',
        component: AbonnePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_GESTIONNAIRE'],
            pageTitle: 'ebankingApp.abonne.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'abonne/:id/edit',
        component: AbonnePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_GESTIONNAIRE'],
            pageTitle: 'ebankingApp.abonne.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'abonne/:id/delete',
        component: AbonneDeletePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_GESTIONNAIRE'],
            pageTitle: 'ebankingApp.abonne.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
