import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { TypeCompteComponent } from './type-compte.component';
import { TypeCompteDetailComponent } from './type-compte-detail.component';
import { TypeComptePopupComponent } from './type-compte-dialog.component';
import { TypeCompteDeletePopupComponent } from './type-compte-delete-dialog.component';

@Injectable()
export class TypeCompteResolvePagingParams implements Resolve<any> {

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

export const typeCompteRoute: Routes = [
    {
        path: 'type-compte',
        component: TypeCompteComponent,
        resolve: {
            'pagingParams': TypeCompteResolvePagingParams
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ebankingApp.typeCompte.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'type-compte/:id',
        component: TypeCompteDetailComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ebankingApp.typeCompte.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const typeComptePopupRoute: Routes = [
    {
        path: 'type-compte-new',
        component: TypeComptePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ebankingApp.typeCompte.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'type-compte/:id/edit',
        component: TypeComptePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ebankingApp.typeCompte.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'type-compte/:id/delete',
        component: TypeCompteDeletePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ebankingApp.typeCompte.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
