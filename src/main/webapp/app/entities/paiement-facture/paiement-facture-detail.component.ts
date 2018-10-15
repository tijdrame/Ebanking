import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { PaiementFacture } from './paiement-facture.model';
import { PaiementFactureService } from './paiement-facture.service';

@Component({
    selector: 'jhi-paiement-facture-detail',
    templateUrl: './paiement-facture-detail.component.html'
})
export class PaiementFactureDetailComponent implements OnInit, OnDestroy {

    paiementFacture: PaiementFacture;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private paiementFactureService: PaiementFactureService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPaiementFactures();
    }

    load(id) {
        this.paiementFactureService.find(id)
            .subscribe((paiementFactureResponse: HttpResponse<PaiementFacture>) => {
                this.paiementFacture = paiementFactureResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPaiementFactures() {
        this.eventSubscriber = this.eventManager.subscribe(
            'paiementFactureListModification',
            (response) => this.load(this.paiementFacture.id)
        );
    }
}
