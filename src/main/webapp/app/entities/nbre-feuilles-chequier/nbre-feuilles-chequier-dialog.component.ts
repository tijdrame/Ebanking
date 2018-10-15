import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { NbreFeuillesChequier } from './nbre-feuilles-chequier.model';
import { NbreFeuillesChequierPopupService } from './nbre-feuilles-chequier-popup.service';
import { NbreFeuillesChequierService } from './nbre-feuilles-chequier.service';

@Component({
    selector: 'jhi-nbre-feuilles-chequier-dialog',
    templateUrl: './nbre-feuilles-chequier-dialog.component.html'
})
export class NbreFeuillesChequierDialogComponent implements OnInit {

    nbreFeuillesChequier: NbreFeuillesChequier;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private nbreFeuillesChequierService: NbreFeuillesChequierService,
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
        if (this.nbreFeuillesChequier.id !== undefined) {
            this.subscribeToSaveResponse(
                this.nbreFeuillesChequierService.update(this.nbreFeuillesChequier));
        } else {
            this.subscribeToSaveResponse(
                this.nbreFeuillesChequierService.create(this.nbreFeuillesChequier));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<NbreFeuillesChequier>>) {
        result.subscribe((res: HttpResponse<NbreFeuillesChequier>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: NbreFeuillesChequier) {
        this.eventManager.broadcast({ name: 'nbreFeuillesChequierListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-nbre-feuilles-chequier-popup',
    template: ''
})
export class NbreFeuillesChequierPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private nbreFeuillesChequierPopupService: NbreFeuillesChequierPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.nbreFeuillesChequierPopupService
                    .open(NbreFeuillesChequierDialogComponent as Component, params['id']);
            } else {
                this.nbreFeuillesChequierPopupService
                    .open(NbreFeuillesChequierDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
