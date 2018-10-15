import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { TypeCompte } from './type-compte.model';
import { TypeComptePopupService } from './type-compte-popup.service';
import { TypeCompteService } from './type-compte.service';

@Component({
    selector: 'jhi-type-compte-dialog',
    templateUrl: './type-compte-dialog.component.html'
})
export class TypeCompteDialogComponent implements OnInit {

    typeCompte: TypeCompte;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private typeCompteService: TypeCompteService,
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
        if (this.typeCompte.id !== undefined) {
            this.subscribeToSaveResponse(
                this.typeCompteService.update(this.typeCompte));
        } else {
            this.subscribeToSaveResponse(
                this.typeCompteService.create(this.typeCompte));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<TypeCompte>>) {
        result.subscribe((res: HttpResponse<TypeCompte>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: TypeCompte) {
        this.eventManager.broadcast({ name: 'typeCompteListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-type-compte-popup',
    template: ''
})
export class TypeComptePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private typeComptePopupService: TypeComptePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.typeComptePopupService
                    .open(TypeCompteDialogComponent as Component, params['id']);
            } else {
                this.typeComptePopupService
                    .open(TypeCompteDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
