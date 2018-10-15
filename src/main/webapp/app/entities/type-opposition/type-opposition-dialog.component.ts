import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { TypeOpposition } from './type-opposition.model';
import { TypeOppositionPopupService } from './type-opposition-popup.service';
import { TypeOppositionService } from './type-opposition.service';

@Component({
    selector: 'jhi-type-opposition-dialog',
    templateUrl: './type-opposition-dialog.component.html'
})
export class TypeOppositionDialogComponent implements OnInit {

    typeOpposition: TypeOpposition;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private typeOppositionService: TypeOppositionService,
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
        if (this.typeOpposition.id !== undefined) {
            this.subscribeToSaveResponse(
                this.typeOppositionService.update(this.typeOpposition));
        } else {
            this.subscribeToSaveResponse(
                this.typeOppositionService.create(this.typeOpposition));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<TypeOpposition>>) {
        result.subscribe((res: HttpResponse<TypeOpposition>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: TypeOpposition) {
        this.eventManager.broadcast({ name: 'typeOppositionListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-type-opposition-popup',
    template: ''
})
export class TypeOppositionPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private typeOppositionPopupService: TypeOppositionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.typeOppositionPopupService
                    .open(TypeOppositionDialogComponent as Component, params['id']);
            } else {
                this.typeOppositionPopupService
                    .open(TypeOppositionDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
