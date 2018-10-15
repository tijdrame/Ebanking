import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Beneficiaire } from './beneficiaire.model';
import { BeneficiairePopupService } from './beneficiaire-popup.service';
import { BeneficiaireService } from './beneficiaire.service';
import { Abonne, AbonneService } from '../abonne';
import { Statut, StatutService } from '../statut';
import { BanquesPartenaires, BanquesPartenairesService } from '../banques-partenaires';

@Component({
    selector: 'jhi-beneficiaire-dialog',
    templateUrl: './beneficiaire-dialog.component.html'
})
export class BeneficiaireDialogComponent implements OnInit {

    beneficiaire: Beneficiaire;
    isSaving: boolean;

    abonnes: Abonne[];

    statuts: Statut[];

    banquespartenaires: BanquesPartenaires[];
    dateDemandeDp: any;
    dateAcceptationDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private beneficiaireService: BeneficiaireService,
        private abonneService: AbonneService,
        private statutService: StatutService,
        private banquesPartenairesService: BanquesPartenairesService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.abonneService.query()
            .subscribe((res: HttpResponse<Abonne[]>) => { this.abonnes = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.statutService.query()
            .subscribe((res: HttpResponse<Statut[]>) => { this.statuts = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.banquesPartenairesService.query()
            .subscribe((res: HttpResponse<BanquesPartenaires[]>) => { this.banquespartenaires = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.beneficiaire.id !== undefined) {
            this.subscribeToSaveResponse(
                this.beneficiaireService.update(this.beneficiaire));
        } else {
            this.subscribeToSaveResponse(
                this.beneficiaireService.create(this.beneficiaire));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Beneficiaire>>) {
        result.subscribe((res: HttpResponse<Beneficiaire>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Beneficiaire) {
        this.eventManager.broadcast({ name: 'beneficiaireListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackAbonneById(index: number, item: Abonne) {
        return item.id;
    }

    trackStatutById(index: number, item: Statut) {
        return item.id;
    }

    trackBanquesPartenairesById(index: number, item: BanquesPartenaires) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-beneficiaire-popup',
    template: ''
})
export class BeneficiairePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private beneficiairePopupService: BeneficiairePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.beneficiairePopupService
                    .open(BeneficiaireDialogComponent as Component, params['id']);
            } else {
                this.beneficiairePopupService
                    .open(BeneficiaireDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
