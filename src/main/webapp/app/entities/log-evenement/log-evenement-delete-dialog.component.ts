import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { LogEvenement } from './log-evenement.model';
import { LogEvenementPopupService } from './log-evenement-popup.service';
import { LogEvenementService } from './log-evenement.service';

@Component({
    selector: 'jhi-log-evenement-delete-dialog',
    templateUrl: './log-evenement-delete-dialog.component.html'
})
export class LogEvenementDeleteDialogComponent {

    logEvenement: LogEvenement;

    constructor(
        private logEvenementService: LogEvenementService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.logEvenementService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'logEvenementListModification',
                content: 'Deleted an logEvenement'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-log-evenement-delete-popup',
    template: ''
})
export class LogEvenementDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private logEvenementPopupService: LogEvenementPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.logEvenementPopupService
                .open(LogEvenementDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
