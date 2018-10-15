import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { OperationsVirement } from './operations-virement.model';
import { OperationsVirementService } from './operations-virement.service';

@Component({
    selector: 'jhi-operations-virement-detail',
    templateUrl: './operations-virement-detail.component.html'
})
export class OperationsVirementDetailComponent implements OnInit, OnDestroy {

    operationsVirement: OperationsVirement;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private operationsVirementService: OperationsVirementService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInOperationsVirements();
    }

    load(id) {
        this.operationsVirementService.find(id)
            .subscribe((operationsVirementResponse: HttpResponse<OperationsVirement>) => {
                this.operationsVirement = operationsVirementResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInOperationsVirements() {
        this.eventSubscriber = this.eventManager.subscribe(
            'operationsVirementListModification',
            (response) => this.load(this.operationsVirement.id)
        );
    }
}
