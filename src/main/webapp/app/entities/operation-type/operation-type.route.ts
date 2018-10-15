import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { OperationTypeComponent } from './operation-type.component';
import { OperationTypeDetailComponent } from './operation-type-detail.component';
import { OperationTypePopupComponent } from './operation-type-dialog.component';
import { OperationTypeDeletePopupComponent } from './operation-type-delete-dialog.component';

@Injectable()
export class OperationTypeResolvePagingParams implements Resolve<any> {

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

export const operationTypeRoute: Routes = [
    {
        path: 'operation-type',
        component: OperationTypeComponent,
        resolve: {
            'pagingParams': OperationTypeResolvePagingParams
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ebankingApp.operationType.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'operation-type/:id',
        component: OperationTypeDetailComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ebankingApp.operationType.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const operationTypePopupRoute: Routes = [
    {
        path: 'operation-type-new',
        component: OperationTypePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ebankingApp.operationType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'operation-type/:id/edit',
        component: OperationTypePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ebankingApp.operationType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'operation-type/:id/delete',
        component: OperationTypeDeletePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ebankingApp.operationType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
