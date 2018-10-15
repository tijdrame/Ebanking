import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { TypeOppositionComponent } from './type-opposition.component';
import { TypeOppositionDetailComponent } from './type-opposition-detail.component';
import { TypeOppositionPopupComponent } from './type-opposition-dialog.component';
import { TypeOppositionDeletePopupComponent } from './type-opposition-delete-dialog.component';

@Injectable()
export class TypeOppositionResolvePagingParams implements Resolve<any> {

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

export const typeOppositionRoute: Routes = [
    {
        path: 'type-opposition',
        component: TypeOppositionComponent,
        resolve: {
            'pagingParams': TypeOppositionResolvePagingParams
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ebankingApp.typeOpposition.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'type-opposition/:id',
        component: TypeOppositionDetailComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ebankingApp.typeOpposition.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const typeOppositionPopupRoute: Routes = [
    {
        path: 'type-opposition-new',
        component: TypeOppositionPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ebankingApp.typeOpposition.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'type-opposition/:id/edit',
        component: TypeOppositionPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ebankingApp.typeOpposition.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'type-opposition/:id/delete',
        component: TypeOppositionDeletePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'ebankingApp.typeOpposition.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
