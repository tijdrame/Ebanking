import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PaiementFacture } from './paiement-facture.model';
import { PaiementFacturePopupService } from './paiement-facture-popup.service';
import { PaiementFactureService } from './paiement-facture.service';

@Component({
    selector: 'jhi-paiement-facture-delete-dialog',
    templateUrl: './paiement-facture-delete-dialog.component.html'
})
export class PaiementFactureDeleteDialogComponent {

    paiementFacture: PaiementFacture;

    constructor(
        private paiementFactureService: PaiementFactureService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.paiementFactureService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'paiementFactureListModification',
                content: 'Deleted an paiementFacture'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-paiement-facture-delete-popup',
    template: ''
})
export class PaiementFactureDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private paiementFacturePopupService: PaiementFacturePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.paiementFacturePopupService
                .open(PaiementFactureDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
