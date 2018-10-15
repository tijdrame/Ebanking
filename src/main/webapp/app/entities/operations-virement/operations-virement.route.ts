import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { OperationsVirementComponent } from './operations-virement.component';
import { OperationsVirementDetailComponent } from './operations-virement-detail.component';
import { OperationsVirementPopupComponent } from './operations-virement-dialog.component';
import { OperationsVirementDeletePopupComponent } from './operations-virement-delete-dialog.component';

@Injectable()
export class OperationsVirementResolvePagingParams implements Resolve<any> {

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

export const operationsVirementRoute: Routes = [
    {
        path: 'operations-virement',
        component: OperationsVirementComponent,
        resolve: {
            'pagingParams': OperationsVirementResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ebankingApp.operationsVirement.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'operations-virement/:id',
        component: OperationsVirementDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ebankingApp.operationsVirement.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const operationsVirementPopupRoute: Routes = [
    {
        path: 'operations-virement-new',
        component: OperationsVirementPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ebankingApp.operationsVirement.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'operations-virement/:id/edit',
        component: OperationsVirementPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ebankingApp.operationsVirement.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'operations-virement/:id/delete',
        component: OperationsVirementDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ebankingApp.operationsVirement.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
