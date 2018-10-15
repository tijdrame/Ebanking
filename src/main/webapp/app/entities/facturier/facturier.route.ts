import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { FacturierComponent } from './facturier.component';
import { FacturierDetailComponent } from './facturier-detail.component';
import { FacturierPopupComponent } from './facturier-dialog.component';
import { FacturierDeletePopupComponent } from './facturier-delete-dialog.component';

@Injectable()
export class FacturierResolvePagingParams implements Resolve<any> {

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

export const facturierRoute: Routes = [
    {
        path: 'facturier',
        component: FacturierComponent,
        resolve: {
            'pagingParams': FacturierResolvePagingParams
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ebankingApp.facturier.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'facturier/:id',
        component: FacturierDetailComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ebankingApp.facturier.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const facturierPopupRoute: Routes = [
    {
        path: 'facturier-new',
        component: FacturierPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ebankingApp.facturier.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'facturier/:id/edit',
        component: FacturierPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ebankingApp.facturier.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'facturier/:id/delete',
        component: FacturierDeletePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ebankingApp.facturier.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
