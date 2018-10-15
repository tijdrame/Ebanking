import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { TypeOpposition } from './type-opposition.model';
import { TypeOppositionService } from './type-opposition.service';

@Component({
    selector: 'jhi-type-opposition-detail',
    templateUrl: './type-opposition-detail.component.html'
})
export class TypeOppositionDetailComponent implements OnInit, OnDestroy {

    typeOpposition: TypeOpposition;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private typeOppositionService: TypeOppositionService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTypeOppositions();
    }

    load(id) {
        this.typeOppositionService.find(id)
            .subscribe((typeOppositionResponse: HttpResponse<TypeOpposition>) => {
                this.typeOpposition = typeOppositionResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTypeOppositions() {
        this.eventSubscriber = this.eventManager.subscribe(
            'typeOppositionListModification',
            (response) => this.load(this.typeOpposition.id)
        );
    }
}
