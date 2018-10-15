import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { NbreFeuillesChequierComponent } from './nbre-feuilles-chequier.component';
import { NbreFeuillesChequierDetailComponent } from './nbre-feuilles-chequier-detail.component';
import { NbreFeuillesChequierPopupComponent } from './nbre-feuilles-chequier-dialog.component';
import { NbreFeuillesChequierDeletePopupComponent } from './nbre-feuilles-chequier-delete-dialog.component';

@Injectable()
export class NbreFeuillesChequierResolvePagingParams implements Resolve<any> {

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

export const nbreFeuillesChequierRoute: Routes = [
    {
        path: 'nbre-feuilles-chequier',
        component: NbreFeuillesChequierComponent,
        resolve: {
            'pagingParams': NbreFeuillesChequierResolvePagingParams
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ebankingApp.nbreFeuillesChequier.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'nbre-feuilles-chequier/:id',
        component: NbreFeuillesChequierDetailComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ebankingApp.nbreFeuillesChequier.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const nbreFeuillesChequierPopupRoute: Routes = [
    {
        path: 'nbre-feuilles-chequier-new',
        component: NbreFeuillesChequierPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ebankingApp.nbreFeuillesChequier.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'nbre-feuilles-chequier/:id/edit',
        component: NbreFeuillesChequierPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ebankingApp.nbreFeuillesChequier.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'nbre-feuilles-chequier/:id/delete',
        component: NbreFeuillesChequierDeletePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ebankingApp.nbreFeuillesChequier.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
