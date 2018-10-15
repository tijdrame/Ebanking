import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { PriseEnCharge } from './prise-en-charge.model';
import { PriseEnChargeService } from './prise-en-charge.service';

@Component({
    selector: 'jhi-prise-en-charge-detail',
    templateUrl: './prise-en-charge-detail.component.html'
})
export class PriseEnChargeDetailComponent implements OnInit, OnDestroy {

    priseEnCharge: PriseEnCharge;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private priseEnChargeService: PriseEnChargeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPriseEnCharges();
    }

    load(id) {
        this.priseEnChargeService.find(id)
            .subscribe((priseEnChargeResponse: HttpResponse<PriseEnCharge>) => {
                this.priseEnCharge = priseEnChargeResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPriseEnCharges() {
        this.eventSubscriber = this.eventManager.subscribe(
            'priseEnChargeListModification',
            (response) => this.load(this.priseEnCharge.id)
        );
    }
}
