import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { TypeChequier } from './type-chequier.model';
import { TypeChequierPopupService } from './type-chequier-popup.service';
import { TypeChequierService } from './type-chequier.service';

@Component({
    selector: 'jhi-type-chequier-dialog',
    templateUrl: './type-chequier-dialog.component.html'
})
export class TypeChequierDialogComponent implements OnInit {

    typeChequier: TypeChequier;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private typeChequierService: TypeChequierService,
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
        if (this.typeChequier.id !== undefined) {
            this.subscribeToSaveResponse(
                this.typeChequierService.update(this.typeChequier));
        } else {
            this.subscribeToSaveResponse(
                this.typeChequierService.create(this.typeChequier));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<TypeChequier>>) {
        result.subscribe((res: HttpResponse<TypeChequier>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: TypeChequier) {
        this.eventManager.broadcast({ name: 'typeChequierListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-type-chequier-popup',
    template: ''
})
export class TypeChequierPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private typeChequierPopupService: TypeChequierPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.typeChequierPopupService
                    .open(TypeChequierDialogComponent as Component, params['id']);
            } else {
                this.typeChequierPopupService
                    .open(TypeChequierDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
