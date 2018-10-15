import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { TypeChequierComponent } from './type-chequier.component';
import { TypeChequierDetailComponent } from './type-chequier-detail.component';
import { TypeChequierPopupComponent } from './type-chequier-dialog.component';
import { TypeChequierDeletePopupComponent } from './type-chequier-delete-dialog.component';

@Injectable()
export class TypeChequierResolvePagingParams implements Resolve<any> {

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

export const typeChequierRoute: Routes = [
    {
        path: 'type-chequier',
        component: TypeChequierComponent,
        resolve: {
            'pagingParams': TypeChequierResolvePagingParams
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ebankingApp.typeChequier.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'type-chequier/:id',
        component: TypeChequierDetailComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ebankingApp.typeChequier.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const typeChequierPopupRoute: Routes = [
    {
        path: 'type-chequier-new',
        component: TypeChequierPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ebankingApp.typeChequier.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'type-chequier/:id/edit',
        component: TypeChequierPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ebankingApp.typeChequier.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'type-chequier/:id/delete',
        component: TypeChequierDeletePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ebankingApp.typeChequier.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
