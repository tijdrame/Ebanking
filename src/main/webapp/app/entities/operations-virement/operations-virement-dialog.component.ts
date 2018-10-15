import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { OperationsVirement } from './operations-virement.model';
import { OperationsVirementPopupService } from './operations-virement-popup.service';
import { OperationsVirementService } from './operations-virement.service';
import { Beneficiaire, BeneficiaireService } from '../beneficiaire';
import { Devise, DeviseService } from '../devise';
import { Statut, StatutService } from '../statut';
import { Operation, OperationService } from '../operation';

@Component({
    selector: 'jhi-operations-virement-dialog',
    templateUrl: './operations-virement-dialog.component.html'
})
export class OperationsVirementDialogComponent implements OnInit {

    operationsVirement: OperationsVirement;
    isSaving: boolean;

    beneficiaires: Beneficiaire[];

    devises: Devise[];

    statuts: Statut[];

    operations: Operation[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private operationsVirementService: OperationsVirementService,
        private beneficiaireService: BeneficiaireService,
        private deviseService: DeviseService,
        private statutService: StatutService,
        private operationService: OperationService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.beneficiaireService.query()
            .subscribe((res: HttpResponse<Beneficiaire[]>) => { this.beneficiaires = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.deviseService.query()
            .subscribe((res: HttpResponse<Devise[]>) => { this.devises = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.statutService.query()
            .subscribe((res: HttpResponse<Statut[]>) => { this.statuts = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.operationService.query()
            .subscribe((res: HttpResponse<Operation[]>) => { this.operations = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.operationsVirement.id !== undefined) {
            this.subscribeToSaveResponse(
                this.operationsVirementService.update(this.operationsVirement));
        } else {
            this.subscribeToSaveResponse(
                this.operationsVirementService.create(this.operationsVirement));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<OperationsVirement>>) {
        result.subscribe((res: HttpResponse<OperationsVirement>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: OperationsVirement) {
        this.eventManager.broadcast({ name: 'operationsVirementListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackBeneficiaireById(index: number, item: Beneficiaire) {
        return item.id;
    }

    trackDeviseById(index: number, item: Devise) {
        return item.id;
    }

    trackStatutById(index: number, item: Statut) {
        return item.id;
    }

    trackOperationById(index: number, item: Operation) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-operations-virement-popup',
    template: ''
})
export class OperationsVirementPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private operationsVirementPopupService: OperationsVirementPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.operationsVirementPopupService
                    .open(OperationsVirementDialogComponent as Component, params['id']);
            } else {
                this.operationsVirementPopupService
                    .open(OperationsVirementDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
