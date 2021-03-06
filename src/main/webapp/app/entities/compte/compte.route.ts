import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { CompteComponent } from './compte.component';
import { CompteDetailComponent } from './compte-detail.component';
import { ComptePopupComponent } from './compte-dialog.component';
import { CompteDeletePopupComponent } from './compte-delete-dialog.component';

@Injectable()
export class CompteResolvePagingParams implements Resolve<any> {

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

export const compteRoute: Routes = [
    {
        path: 'compte',
        component: CompteComponent,
        resolve: {
            'pagingParams': CompteResolvePagingParams
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_USER', 'ROLE_GESTIONNAIRE'],
            pageTitle: 'ebankingApp.compte.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'compte/:id',
        component: CompteDetailComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN', 'ROLE_GESTIONNAIRE'],
            pageTitle: 'ebankingApp.compte.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const comptePopupRoute: Routes = [
    {
        path: 'compte-new',
        component: ComptePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_GESTIONNAIRE'],
            pageTitle: 'ebankingApp.compte.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'compte/:id/edit',
        component: ComptePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_GESTIONNAIRE'],
            pageTitle: 'ebankingApp.compte.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'compte/:id/delete',
        component: CompteDeletePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_GESTIONNAIRE'],
            pageTitle: 'ebankingApp.compte.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
