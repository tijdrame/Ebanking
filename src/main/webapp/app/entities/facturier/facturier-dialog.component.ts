import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Facturier } from './facturier.model';
import { FacturierPopupService } from './facturier-popup.service';
import { FacturierService } from './facturier.service';

@Component({
    selector: 'jhi-facturier-dialog',
    templateUrl: './facturier-dialog.component.html'
})
export class FacturierDialogComponent implements OnInit {

    facturier: Facturier;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private facturierService: FacturierService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.facturier.id !== undefined) {
            this.subscribeToSaveResponse(
                this.facturierService.update(this.facturier));
        } else {
            this.subscribeToSaveResponse(
                this.facturierService.create(this.facturier));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Facturier>>) {
        result.subscribe((res: HttpResponse<Facturier>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Facturier) {
        this.eventManager.broadcast({ name: 'facturierListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-facturier-popup',
    template: ''
})
export class FacturierPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private facturierPopupService: FacturierPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.facturierPopupService
                    .open(FacturierDialogComponent as Component, params['id']);
            } else {
                this.facturierPopupService
                    .open(FacturierDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
