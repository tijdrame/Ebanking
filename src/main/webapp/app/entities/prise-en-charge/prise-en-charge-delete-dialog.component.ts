import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PriseEnCharge } from './prise-en-charge.model';
import { PriseEnChargePopupService } from './prise-en-charge-popup.service';
import { PriseEnChargeService } from './prise-en-charge.service';

@Component({
    selector: 'jhi-prise-en-charge-delete-dialog',
    templateUrl: './prise-en-charge-delete-dialog.component.html'
})
export class PriseEnChargeDeleteDialogComponent {

    priseEnCharge: PriseEnCharge;

    constructor(
        private priseEnChargeService: PriseEnChargeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.priseEnChargeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'priseEnChargeListModification',
                content: 'Deleted an priseEnCharge'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-prise-en-charge-delete-popup',
    template: ''
})
export class PriseEnChargeDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private priseEnChargePopupService: PriseEnChargePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.priseEnChargePopupService
                .open(PriseEnChargeDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
