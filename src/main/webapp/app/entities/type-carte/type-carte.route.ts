import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { TypeCarteComponent } from './type-carte.component';
import { TypeCarteDetailComponent } from './type-carte-detail.component';
import { TypeCartePopupComponent } from './type-carte-dialog.component';
import { TypeCarteDeletePopupComponent } from './type-carte-delete-dialog.component';

@Injectable()
export class TypeCarteResolvePagingParams implements Resolve<any> {

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

export const typeCarteRoute: Routes = [
    {
        path: 'type-carte',
        component: TypeCarteComponent,
        resolve: {
            'pagingParams': TypeCarteResolvePagingParams
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ebankingApp.typeCarte.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'type-carte/:id',
        component: TypeCarteDetailComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ebankingApp.typeCarte.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const typeCartePopupRoute: Routes = [
    {
        path: 'type-carte-new',
        component: TypeCartePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ebankingApp.typeCarte.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'type-carte/:id/edit',
        component: TypeCartePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ebankingApp.typeCarte.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'type-carte/:id/delete',
        component: TypeCarteDeletePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ebankingApp.typeCarte.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
