import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { BanquesPartenaires } from './banques-partenaires.model';
import { BanquesPartenairesPopupService } from './banques-partenaires-popup.service';
import { BanquesPartenairesService } from './banques-partenaires.service';

@Component({
    selector: 'jhi-banques-partenaires-dialog',
    templateUrl: './banques-partenaires-dialog.component.html'
})
export class BanquesPartenairesDialogComponent implements OnInit {

    banquesPartenaires: BanquesPartenaires;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private banquesPartenairesService: BanquesPartenairesService,
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
        if (this.banquesPartenaires.id !== undefined) {
            this.subscribeToSaveResponse(
                this.banquesPartenairesService.update(this.banquesPartenaires));
        } else {
            this.subscribeToSaveResponse(
                this.banquesPartenairesService.create(this.banquesPartenaires));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<BanquesPartenaires>>) {
        result.subscribe((res: HttpResponse<BanquesPartenaires>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: BanquesPartenaires) {
        this.eventManager.broadcast({ name: 'banquesPartenairesListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-banques-partenaires-popup',
    template: ''
})
export class BanquesPartenairesPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private banquesPartenairesPopupService: BanquesPartenairesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.banquesPartenairesPopupService
                    .open(BanquesPartenairesDialogComponent as Component, params['id']);
            } else {
                this.banquesPartenairesPopupService
                    .open(BanquesPartenairesDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
