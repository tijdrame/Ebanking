import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { LogEvenement } from './log-evenement.model';
import { LogEvenementService } from './log-evenement.service';

@Component({
    selector: 'jhi-log-evenement-detail',
    templateUrl: './log-evenement-detail.component.html'
})
export class LogEvenementDetailComponent implements OnInit, OnDestroy {

    logEvenement: LogEvenement;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private logEvenementService: LogEvenementService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInLogEvenements();
    }

    load(id) {
        this.logEvenementService.find(id)
            .subscribe((logEvenementResponse: HttpResponse<LogEvenement>) => {
                this.logEvenement = logEvenementResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInLogEvenements() {
        this.eventSubscriber = this.eventManager.subscribe(
            'logEvenementListModification',
            (response) => this.load(this.logEvenement.id)
        );
    }
}
