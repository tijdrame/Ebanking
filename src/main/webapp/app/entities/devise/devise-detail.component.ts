import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Devise } from './devise.model';
import { DeviseService } from './devise.service';

@Component({
    selector: 'jhi-devise-detail',
    templateUrl: './devise-detail.component.html'
})
export class DeviseDetailComponent implements OnInit, OnDestroy {

    devise: Devise;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private deviseService: DeviseService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDevises();
    }

    load(id) {
        this.deviseService.find(id)
            .subscribe((deviseResponse: HttpResponse<Devise>) => {
                this.devise = deviseResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInDevises() {
        this.eventSubscriber = this.eventManager.subscribe(
            'deviseListModification',
            (response) => this.load(this.devise.id)
        );
    }
}
