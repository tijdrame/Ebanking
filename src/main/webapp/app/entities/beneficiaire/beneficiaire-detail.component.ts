import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Beneficiaire } from './beneficiaire.model';
import { BeneficiaireService } from './beneficiaire.service';

@Component({
    selector: 'jhi-beneficiaire-detail',
    templateUrl: './beneficiaire-detail.component.html'
})
export class BeneficiaireDetailComponent implements OnInit, OnDestroy {

    beneficiaire: Beneficiaire;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private beneficiaireService: BeneficiaireService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInBeneficiaires();
    }

    load(id) {
        this.beneficiaireService.find(id)
            .subscribe((beneficiaireResponse: HttpResponse<Beneficiaire>) => {
                this.beneficiaire = beneficiaireResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInBeneficiaires() {
        this.eventSubscriber = this.eventManager.subscribe(
            'beneficiaireListModification',
            (response) => this.load(this.beneficiaire.id)
        );
    }
}
