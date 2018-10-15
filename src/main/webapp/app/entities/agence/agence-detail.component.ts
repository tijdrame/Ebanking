import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Agence } from './agence.model';
import { AgenceService } from './agence.service';

@Component({
    selector: 'jhi-agence-detail',
    templateUrl: './agence-detail.component.html'
})
export class AgenceDetailComponent implements OnInit, OnDestroy {

    agence: Agence;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private agenceService: AgenceService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAgences();
    }

    load(id) {
        this.agenceService.find(id)
            .subscribe((agenceResponse: HttpResponse<Agence>) => {
                this.agence = agenceResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAgences() {
        this.eventSubscriber = this.eventManager.subscribe(
            'agenceListModification',
            (response) => this.load(this.agence.id)
        );
    }
}
