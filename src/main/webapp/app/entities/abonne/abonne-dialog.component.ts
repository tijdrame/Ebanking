import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import {JhiEventManager, JhiAlertService, JhiLanguageService} from 'ng-jhipster';

import { Abonne } from './abonne.model';
import { AbonnePopupService } from './abonne-popup.service';
import { AbonneService } from './abonne.service';
import { Agence, AgenceService } from '../agence';
import {EMAIL_ALREADY_USED_TYPE, LOGIN_ALREADY_USED_TYPE, User, UserService} from '../../shared';
import {Register} from "../../account/register/register.service";

@Component({
    selector: 'jhi-abonne-dialog',
    templateUrl: './abonne-dialog.component.html'
})
export class AbonneDialogComponent implements OnInit {

    confirmPassword: string;
    doNotMatch: string;
    error: string;
    errorEmailExists: string;
    errorUserExists: string;
    success: boolean;

    abonne: Abonne;
    isSaving: boolean;
    registerAccount: any;

    agences: Agence[];

    users: User[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private abonneService: AbonneService,
        private agenceService: AgenceService,
        private userService: UserService,
        private languageService: JhiLanguageService,
        private registerService: Register,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.registerAccount = {};
        this.isSaving = false;
        this.agenceService.query()
            .subscribe((res: HttpResponse<Agence[]>) => { this.agences = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.userService.query()
            .subscribe((res: HttpResponse<User[]>) => { this.users = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.abonne.id !== undefined) {
            this.subscribeToSaveResponse(
                this.abonneService.update(this.abonne));
        } else {
            this.subscribeToSaveResponse(
                this.abonneService.create(this.abonne));
        }
    }

    register() {
        this.isSaving = true;
        if(this.abonne.id){
            this.save();
            return;
        }

        if (this.registerAccount.password !== this.confirmPassword) {
            this.doNotMatch = 'ERROR';
        } else {
            this.doNotMatch = null;
            this.error = null;
            this.errorUserExists = null;
            this.errorEmailExists = null;
            this.languageService.getCurrent().then((key) => {
                this.registerAccount.langKey = key;
                this.registerService.save(this.registerAccount).subscribe(() => {
                    this.success = true;
                    this.activeModal.dismiss(true);
                }, (response) => this.processError(response));
            });
        }
    }

    private processError(response) {
        this.success = null;
        if (response.status === 400 && response.json().type === LOGIN_ALREADY_USED_TYPE) {
            this.errorUserExists = 'ERROR';
        } else if (response.status === 400 && response.json().type === EMAIL_ALREADY_USED_TYPE) {
            this.errorEmailExists = 'ERROR';
        } else {
            this.error = 'ERROR';
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Abonne>>) {
        result.subscribe((res: HttpResponse<Abonne>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Abonne) {
        this.eventManager.broadcast({ name: 'abonneListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackAgenceById(index: number, item: Agence) {
        return item.id;
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-abonne-popup',
    template: ''
})
export class AbonnePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private abonnePopupService: AbonnePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.abonnePopupService
                    .open(AbonneDialogComponent as Component, params['id']);
            } else {
                this.abonnePopupService
                    .open(AbonneDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
