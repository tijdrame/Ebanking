import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Agence } from './agence.model';
import { AgencePopupService } from './agence-popup.service';
import { AgenceService } from './agence.service';

@Component({
    selector: 'jhi-agence-dialog',
    templateUrl: './agence-dialog.component.html'
})
export class AgenceDialogComponent implements OnInit {

    agence: Agence;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private agenceService: AgenceService,
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
        if (this.agence.id !== undefined) {
            this.subscribeToSaveResponse(
                this.agenceService.update(this.agence));
        } else {
            this.subscribeToSaveResponse(
                this.agenceService.create(this.agence));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Agence>>) {
        result.subscribe((res: HttpResponse<Agence>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Agence) {
        this.eventManager.broadcast({ name: 'agenceListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-agence-popup',
    template: ''
})
export class AgencePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private agencePopupService: AgencePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.agencePopupService
                    .open(AgenceDialogComponent as Component, params['id']);
            } else {
                this.agencePopupService
                    .open(AgenceDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
