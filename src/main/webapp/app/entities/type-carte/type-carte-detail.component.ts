import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { TypeCarte } from './type-carte.model';
import { TypeCarteService } from './type-carte.service';

@Component({
    selector: 'jhi-type-carte-detail',
    templateUrl: './type-carte-detail.component.html'
})
export class TypeCarteDetailComponent implements OnInit, OnDestroy {

    typeCarte: TypeCarte;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private typeCarteService: TypeCarteService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTypeCartes();
    }

    load(id) {
        this.typeCarteService.find(id)
            .subscribe((typeCarteResponse: HttpResponse<TypeCarte>) => {
                this.typeCarte = typeCarteResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTypeCartes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'typeCarteListModification',
            (response) => this.load(this.typeCarte.id)
        );
    }
}
