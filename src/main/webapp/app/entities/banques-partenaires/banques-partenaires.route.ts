import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { BanquesPartenairesComponent } from './banques-partenaires.component';
import { BanquesPartenairesDetailComponent } from './banques-partenaires-detail.component';
import { BanquesPartenairesPopupComponent } from './banques-partenaires-dialog.component';
import { BanquesPartenairesDeletePopupComponent } from './banques-partenaires-delete-dialog.component';

@Injectable()
export class BanquesPartenairesResolvePagingParams implements Resolve<any> {

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

export const banquesPartenairesRoute: Routes = [
    {
        path: 'banques-partenaires',
        component: BanquesPartenairesComponent,
        resolve: {
            'pagingParams': BanquesPartenairesResolvePagingParams
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ebankingApp.banquesPartenaires.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'banques-partenaires/:id',
        component: BanquesPartenairesDetailComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ebankingApp.banquesPartenaires.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const banquesPartenairesPopupRoute: Routes = [
    {
        path: 'banques-partenaires-new',
        component: BanquesPartenairesPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ebankingApp.banquesPartenaires.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'banques-partenaires/:id/edit',
        component: BanquesPartenairesPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ebankingApp.banquesPartenaires.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'banques-partenaires/:id/delete',
        component: BanquesPartenairesDeletePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ebankingApp.banquesPartenaires.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
