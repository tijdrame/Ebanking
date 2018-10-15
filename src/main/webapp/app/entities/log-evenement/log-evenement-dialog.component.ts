import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { LogEvenement } from './log-evenement.model';
import { LogEvenementPopupService } from './log-evenement-popup.service';
import { LogEvenementService } from './log-evenement.service';
import { User, UserService } from '../../shared';

@Component({
    selector: 'jhi-log-evenement-dialog',
    templateUrl: './log-evenement-dialog.component.html'
})
export class LogEvenementDialogComponent implements OnInit {

    logEvenement: LogEvenement;
    isSaving: boolean;

    users: User[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private logEvenementService: LogEvenementService,
        private userService: UserService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.userService.query()
            .subscribe((res: HttpResponse<User[]>) => { this.users = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.logEvenement.id !== undefined) {
            this.subscribeToSaveResponse(
                this.logEvenementService.update(this.logEvenement));
        } else {
            this.subscribeToSaveResponse(
                this.logEvenementService.create(this.logEvenement));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<LogEvenement>>) {
        result.subscribe((res: HttpResponse<LogEvenement>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: LogEvenement) {
        this.eventManager.broadcast({ name: 'logEvenementListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-log-evenement-popup',
    template: ''
})
export class LogEvenementPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private logEvenementPopupService: LogEvenementPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.logEvenementPopupService
                    .open(LogEvenementDialogComponent as Component, params['id']);
            } else {
                this.logEvenementPopupService
                    .open(LogEvenementDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
