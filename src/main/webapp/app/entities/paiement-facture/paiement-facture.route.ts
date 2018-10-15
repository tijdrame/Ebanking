import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { PaiementFactureComponent } from './paiement-facture.component';
import { PaiementFactureDetailComponent } from './paiement-facture-detail.component';
import { PaiementFacturePopupComponent } from './paiement-facture-dialog.component';
import { PaiementFactureDeletePopupComponent } from './paiement-facture-delete-dialog.component';

@Injectable()
export class PaiementFactureResolvePagingParams implements Resolve<any> {

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

export const paiementFactureRoute: Routes = [
    {
        path: 'paiement-facture',
        component: PaiementFactureComponent,
        resolve: {
            'pagingParams': PaiementFactureResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ebankingApp.paiementFacture.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'paiement-facture/:id',
        component: PaiementFactureDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ebankingApp.paiementFacture.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const paiementFacturePopupRoute: Routes = [
    {
        path: 'paiement-facture-new',
        component: PaiementFacturePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ebankingApp.paiementFacture.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'paiement-facture/:id/edit',
        component: PaiementFacturePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ebankingApp.paiementFacture.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'paiement-facture/:id/delete',
        component: PaiementFactureDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ebankingApp.paiementFacture.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
