import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { TypeAbonneComponent } from './type-abonne.component';
import { TypeAbonneDetailComponent } from './type-abonne-detail.component';
import { TypeAbonnePopupComponent } from './type-abonne-dialog.component';
import { TypeAbonneDeletePopupComponent } from './type-abonne-delete-dialog.component';

@Injectable()
export class TypeAbonneResolvePagingParams implements Resolve<any> {

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

export const typeAbonneRoute: Routes = [
    {
        path: 'type-abonne',
        component: TypeAbonneComponent,
        resolve: {
            'pagingParams': TypeAbonneResolvePagingParams
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ebankingApp.typeAbonne.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'type-abonne/:id',
        component: TypeAbonneDetailComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ebankingApp.typeAbonne.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const typeAbonnePopupRoute: Routes = [
    {
        path: 'type-abonne-new',
        component: TypeAbonnePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ebankingApp.typeAbonne.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'type-abonne/:id/edit',
        component: TypeAbonnePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ebankingApp.typeAbonne.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'type-abonne/:id/delete',
        component: TypeAbonneDeletePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ebankingApp.typeAbonne.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
