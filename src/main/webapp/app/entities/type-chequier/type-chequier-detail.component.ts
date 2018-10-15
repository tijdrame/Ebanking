import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { TypeChequier } from './type-chequier.model';
import { TypeChequierService } from './type-chequier.service';

@Component({
    selector: 'jhi-type-chequier-detail',
    templateUrl: './type-chequier-detail.component.html'
})
export class TypeChequierDetailComponent implements OnInit, OnDestroy {

    typeChequier: TypeChequier;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private typeChequierService: TypeChequierService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTypeChequiers();
    }

    load(id) {
        this.typeChequierService.find(id)
            .subscribe((typeChequierResponse: HttpResponse<TypeChequier>) => {
                this.typeChequier = typeChequierResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTypeChequiers() {
        this.eventSubscriber = this.eventManager.subscribe(
            'typeChequierListModification',
            (response) => this.load(this.typeChequier.id)
        );
    }
}
