import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { OperationType } from './operation-type.model';
import { OperationTypeService } from './operation-type.service';

@Component({
    selector: 'jhi-operation-type-detail',
    templateUrl: './operation-type-detail.component.html'
})
export class OperationTypeDetailComponent implements OnInit, OnDestroy {

    operationType: OperationType;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private operationTypeService: OperationTypeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInOperationTypes();
    }

    load(id) {
        this.operationTypeService.find(id)
            .subscribe((operationTypeResponse: HttpResponse<OperationType>) => {
                this.operationType = operationTypeResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInOperationTypes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'operationTypeListModification',
            (response) => this.load(this.operationType.id)
        );
    }
}
