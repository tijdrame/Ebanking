import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PriseEnCharge } from './prise-en-charge.model';
import { PriseEnChargePopupService } from './prise-en-charge-popup.service';
import { PriseEnChargeService } from './prise-en-charge.service';

@Component({
    selector: 'jhi-prise-en-charge-dialog',
    templateUrl: './prise-en-charge-dialog.component.html'
})
export class PriseEnChargeDialogComponent implements OnInit {

    priseEnCharge: PriseEnCharge;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private priseEnChargeService: PriseEnChargeService,
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
        if (this.priseEnCharge.id !== undefined) {
            this.subscribeToSaveResponse(
                this.priseEnChargeService.update(this.priseEnCharge));
        } else {
            this.subscribeToSaveResponse(
                this.priseEnChargeService.create(this.priseEnCharge));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<PriseEnCharge>>) {
        result.subscribe((res: HttpResponse<PriseEnCharge>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: PriseEnCharge) {
        this.eventManager.broadcast({ name: 'priseEnChargeListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-prise-en-charge-popup',
    template: ''
})
export class PriseEnChargePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private priseEnChargePopupService: PriseEnChargePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.priseEnChargePopupService
                    .open(PriseEnChargeDialogComponent as Component, params['id']);
            } else {
                this.priseEnChargePopupService
                    .open(PriseEnChargeDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
