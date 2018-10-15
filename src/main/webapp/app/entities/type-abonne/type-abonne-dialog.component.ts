import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { TypeAbonne } from './type-abonne.model';
import { TypeAbonnePopupService } from './type-abonne-popup.service';
import { TypeAbonneService } from './type-abonne.service';

@Component({
    selector: 'jhi-type-abonne-dialog',
    templateUrl: './type-abonne-dialog.component.html'
})
export class TypeAbonneDialogComponent implements OnInit {

    typeAbonne: TypeAbonne;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private typeAbonneService: TypeAbonneService,
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
        if (this.typeAbonne.id !== undefined) {
            this.subscribeToSaveResponse(
                this.typeAbonneService.update(this.typeAbonne));
        } else {
            this.subscribeToSaveResponse(
                this.typeAbonneService.create(this.typeAbonne));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<TypeAbonne>>) {
        result.subscribe((res: HttpResponse<TypeAbonne>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: TypeAbonne) {
        this.eventManager.broadcast({ name: 'typeAbonneListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-type-abonne-popup',
    template: ''
})
export class TypeAbonnePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private typeAbonnePopupService: TypeAbonnePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.typeAbonnePopupService
                    .open(TypeAbonneDialogComponent as Component, params['id']);
            } else {
                this.typeAbonnePopupService
                    .open(TypeAbonneDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
