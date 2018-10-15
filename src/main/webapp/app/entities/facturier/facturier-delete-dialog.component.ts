import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Facturier } from './facturier.model';
import { FacturierPopupService } from './facturier-popup.service';
import { FacturierService } from './facturier.service';

@Component({
    selector: 'jhi-facturier-delete-dialog',
    templateUrl: './facturier-delete-dialog.component.html'
})
export class FacturierDeleteDialogComponent {

    facturier: Facturier;

    constructor(
        private facturierService: FacturierService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.facturierService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'facturierListModification',
                content: 'Deleted an facturier'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-facturier-delete-popup',
    template: ''
})
export class FacturierDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private facturierPopupService: FacturierPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.facturierPopupService
                .open(FacturierDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
