import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Facturier } from './facturier.model';
import { FacturierService } from './facturier.service';

@Component({
    selector: 'jhi-facturier-detail',
    templateUrl: './facturier-detail.component.html'
})
export class FacturierDetailComponent implements OnInit, OnDestroy {

    facturier: Facturier;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private facturierService: FacturierService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInFacturiers();
    }

    load(id) {
        this.facturierService.find(id)
            .subscribe((facturierResponse: HttpResponse<Facturier>) => {
                this.facturier = facturierResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInFacturiers() {
        this.eventSubscriber = this.eventManager.subscribe(
            'facturierListModification',
            (response) => this.load(this.facturier.id)
        );
    }
}
