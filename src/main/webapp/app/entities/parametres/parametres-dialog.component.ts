import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Parametres } from './parametres.model';
import { ParametresPopupService } from './parametres-popup.service';
import { ParametresService } from './parametres.service';

@Component({
    selector: 'jhi-parametres-dialog',
    templateUrl: './parametres-dialog.component.html'
})
export class ParametresDialogComponent implements OnInit {

    parametres: Parametres;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private parametresService: ParametresService,
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
        if (this.parametres.id !== undefined) {
            this.subscribeToSaveResponse(
                this.parametresService.update(this.parametres));
        } else {
            this.subscribeToSaveResponse(
                this.parametresService.create(this.parametres));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Parametres>>) {
        result.subscribe((res: HttpResponse<Parametres>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Parametres) {
        this.eventManager.broadcast({ name: 'parametresListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-parametres-popup',
    template: ''
})
export class ParametresPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private parametresPopupService: ParametresPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.parametresPopupService
                    .open(ParametresDialogComponent as Component, params['id']);
            } else {
                this.parametresPopupService
                    .open(ParametresDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
