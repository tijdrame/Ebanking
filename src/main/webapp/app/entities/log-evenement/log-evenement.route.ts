import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { LogEvenementComponent } from './log-evenement.component';
import { LogEvenementDetailComponent } from './log-evenement-detail.component';
import { LogEvenementPopupComponent } from './log-evenement-dialog.component';
import { LogEvenementDeletePopupComponent } from './log-evenement-delete-dialog.component';

@Injectable()
export class LogEvenementResolvePagingParams implements Resolve<any> {

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

export const logEvenementRoute: Routes = [
    {
        path: 'log-evenement',
        component: LogEvenementComponent,
        resolve: {
            'pagingParams': LogEvenementResolvePagingParams
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ebankingApp.logEvenement.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'log-evenement/:id',
        component: LogEvenementDetailComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ebankingApp.logEvenement.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const logEvenementPopupRoute: Routes = [
    {
        path: 'log-evenement-new',
        component: LogEvenementPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ebankingApp.logEvenement.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'log-evenement/:id/edit',
        component: LogEvenementPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ebankingApp.logEvenement.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'log-evenement/:id/delete',
        component: LogEvenementDeletePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ebankingApp.logEvenement.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
