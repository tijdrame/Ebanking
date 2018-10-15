import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PaiementFacture } from './paiement-facture.model';
import { PaiementFacturePopupService } from './paiement-facture-popup.service';
import { PaiementFactureService } from './paiement-facture.service';
import { Devise, DeviseService } from '../devise';
import { Compte, CompteService } from '../compte';
import { Facturier, FacturierService } from '../facturier';
import { Statut, StatutService } from '../statut';

@Component({
    selector: 'jhi-paiement-facture-dialog',
    templateUrl: './paiement-facture-dialog.component.html'
})
export class PaiementFactureDialogComponent implements OnInit {

    paiementFacture: PaiementFacture;
    isSaving: boolean;

    devises: Devise[];

    comptes: Compte[];

    facturiers: Facturier[];

    statuts: Statut[];
    dateDebutDp: any;
    dateFinDp: any;
    dateAcceptationDp: any;
    dateDemandeDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private paiementFactureService: PaiementFactureService,
        private deviseService: DeviseService,
        private compteService: CompteService,
        private facturierService: FacturierService,
        private statutService: StatutService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.deviseService.query()
            .subscribe((res: HttpResponse<Devise[]>) => { this.devises = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.compteService.query()
            .subscribe((res: HttpResponse<Compte[]>) => { this.comptes = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.facturierService.query()
            .subscribe((res: HttpResponse<Facturier[]>) => { this.facturiers = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.statutService.query()
            .subscribe((res: HttpResponse<Statut[]>) => { this.statuts = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.paiementFacture.id !== undefined) {
            this.subscribeToSaveResponse(
                this.paiementFactureService.update(this.paiementFacture));
        } else {
            this.subscribeToSaveResponse(
                this.paiementFactureService.create(this.paiementFacture));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<PaiementFacture>>) {
        result.subscribe((res: HttpResponse<PaiementFacture>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: PaiementFacture) {
        this.eventManager.broadcast({ name: 'paiementFactureListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackDeviseById(index: number, item: Devise) {
        return item.id;
    }

    trackCompteById(index: number, item: Compte) {
        return item.id;
    }

    trackFacturierById(index: number, item: Facturier) {
        return item.id;
    }

    trackStatutById(index: number, item: Statut) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-paiement-facture-popup',
    template: ''
})
export class PaiementFacturePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private paiementFacturePopupService: PaiementFacturePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.paiementFacturePopupService
                    .open(PaiementFactureDialogComponent as Component, params['id']);
            } else {
                this.paiementFacturePopupService
                    .open(PaiementFactureDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
