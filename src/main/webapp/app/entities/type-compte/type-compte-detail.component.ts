import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { TypeCompte } from './type-compte.model';
import { TypeCompteService } from './type-compte.service';

@Component({
    selector: 'jhi-type-compte-detail',
    templateUrl: './type-compte-detail.component.html'
})
export class TypeCompteDetailComponent implements OnInit, OnDestroy {

    typeCompte: TypeCompte;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private typeCompteService: TypeCompteService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTypeComptes();
    }

    load(id) {
        this.typeCompteService.find(id)
            .subscribe((typeCompteResponse: HttpResponse<TypeCompte>) => {
                this.typeCompte = typeCompteResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTypeComptes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'typeCompteListModification',
            (response) => this.load(this.typeCompte.id)
        );
    }
}
