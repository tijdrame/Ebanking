import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { PriseEnChargeComponent } from './prise-en-charge.component';
import { PriseEnChargeDetailComponent } from './prise-en-charge-detail.component';
import { PriseEnChargePopupComponent } from './prise-en-charge-dialog.component';
import { PriseEnChargeDeletePopupComponent } from './prise-en-charge-delete-dialog.component';

@Injectable()
export class PriseEnChargeResolvePagingParams implements Resolve<any> {

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

export const priseEnChargeRoute: Routes = [
    {
        path: 'prise-en-charge',
        component: PriseEnChargeComponent,
        resolve: {
            'pagingParams': PriseEnChargeResolvePagingParams
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ebankingApp.priseEnCharge.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'prise-en-charge/:id',
        component: PriseEnChargeDetailComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ebankingApp.priseEnCharge.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const priseEnChargePopupRoute: Routes = [
    {
        path: 'prise-en-charge-new',
        component: PriseEnChargePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ebankingApp.priseEnCharge.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'prise-en-charge/:id/edit',
        component: PriseEnChargePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ebankingApp.priseEnCharge.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'prise-en-charge/:id/delete',
        component: PriseEnChargeDeletePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ebankingApp.priseEnCharge.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
