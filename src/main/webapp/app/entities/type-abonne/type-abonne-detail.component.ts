import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { TypeAbonne } from './type-abonne.model';
import { TypeAbonneService } from './type-abonne.service';

@Component({
    selector: 'jhi-type-abonne-detail',
    templateUrl: './type-abonne-detail.component.html'
})
export class TypeAbonneDetailComponent implements OnInit, OnDestroy {

    typeAbonne: TypeAbonne;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private typeAbonneService: TypeAbonneService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTypeAbonnes();
    }

    load(id) {
        this.typeAbonneService.find(id)
            .subscribe((typeAbonneResponse: HttpResponse<TypeAbonne>) => {
                this.typeAbonne = typeAbonneResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTypeAbonnes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'typeAbonneListModification',
            (response) => this.load(this.typeAbonne.id)
        );
    }
}
