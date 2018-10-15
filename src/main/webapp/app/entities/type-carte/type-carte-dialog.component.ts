import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { TypeCarte } from './type-carte.model';
import { TypeCartePopupService } from './type-carte-popup.service';
import { TypeCarteService } from './type-carte.service';

@Component({
    selector: 'jhi-type-carte-dialog',
    templateUrl: './type-carte-dialog.component.html'
})
export class TypeCarteDialogComponent implements OnInit {

    typeCarte: TypeCarte;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private typeCarteService: TypeCarteService,
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
        if (this.typeCarte.id !== undefined) {
            this.subscribeToSaveResponse(
                this.typeCarteService.update(this.typeCarte));
        } else {
            this.subscribeToSaveResponse(
                this.typeCarteService.create(this.typeCarte));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<TypeCarte>>) {
        result.subscribe((res: HttpResponse<TypeCarte>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: TypeCarte) {
        this.eventManager.broadcast({ name: 'typeCarteListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-type-carte-popup',
    template: ''
})
export class TypeCartePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private typeCartePopupService: TypeCartePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.typeCartePopupService
                    .open(TypeCarteDialogComponent as Component, params['id']);
            } else {
                this.typeCartePopupService
                    .open(TypeCarteDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
