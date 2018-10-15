import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { BeneficiaireComponent } from './beneficiaire.component';
import { BeneficiaireDetailComponent } from './beneficiaire-detail.component';
import { BeneficiairePopupComponent } from './beneficiaire-dialog.component';
import { BeneficiaireDeletePopupComponent } from './beneficiaire-delete-dialog.component';

@Injectable()
export class BeneficiaireResolvePagingParams implements Resolve<any> {

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

export const beneficiaireRoute: Routes = [
    {
        path: 'beneficiaire',
        component: BeneficiaireComponent,
        resolve: {
            'pagingParams': BeneficiaireResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ebankingApp.beneficiaire.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'beneficiaire/:id',
        component: BeneficiaireDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ebankingApp.beneficiaire.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const beneficiairePopupRoute: Routes = [
    {
        path: 'beneficiaire-new',
        component: BeneficiairePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ebankingApp.beneficiaire.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'beneficiaire/:id/edit',
        component: BeneficiairePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ebankingApp.beneficiaire.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'beneficiaire/:id/delete',
        component: BeneficiaireDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ebankingApp.beneficiaire.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
