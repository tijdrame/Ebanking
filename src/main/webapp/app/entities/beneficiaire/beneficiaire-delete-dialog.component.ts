import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Beneficiaire } from './beneficiaire.model';
import { BeneficiairePopupService } from './beneficiaire-popup.service';
import { BeneficiaireService } from './beneficiaire.service';

@Component({
    selector: 'jhi-beneficiaire-delete-dialog',
    templateUrl: './beneficiaire-delete-dialog.component.html'
})
export class BeneficiaireDeleteDialogComponent {

    beneficiaire: Beneficiaire;

    constructor(
        private beneficiaireService: BeneficiaireService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.beneficiaireService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'beneficiaireListModification',
                content: 'Deleted an beneficiaire'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-beneficiaire-delete-popup',
    template: ''
})
export class BeneficiaireDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private beneficiairePopupService: BeneficiairePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.beneficiairePopupService
                .open(BeneficiaireDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
