import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Devise } from './devise.model';
import { DevisePopupService } from './devise-popup.service';
import { DeviseService } from './devise.service';

@Component({
    selector: 'jhi-devise-dialog',
    templateUrl: './devise-dialog.component.html'
})
export class DeviseDialogComponent implements OnInit {

    devise: Devise;
    isSaving: boolean;
    dateMajDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private deviseService: DeviseService,
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
        if (this.devise.id !== undefined) {
            this.subscribeToSaveResponse(
                this.deviseService.update(this.devise));
        } else {
            this.subscribeToSaveResponse(
                this.deviseService.create(this.devise));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Devise>>) {
        result.subscribe((res: HttpResponse<Devise>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Devise) {
        this.eventManager.broadcast({ name: 'deviseListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-devise-popup',
    template: ''
})
export class DevisePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private devisePopupService: DevisePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.devisePopupService
                    .open(DeviseDialogComponent as Component, params['id']);
            } else {
                this.devisePopupService
                    .open(DeviseDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
