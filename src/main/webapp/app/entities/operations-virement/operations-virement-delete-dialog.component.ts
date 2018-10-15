import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { OperationsVirement } from './operations-virement.model';
import { OperationsVirementPopupService } from './operations-virement-popup.service';
import { OperationsVirementService } from './operations-virement.service';

@Component({
    selector: 'jhi-operations-virement-delete-dialog',
    templateUrl: './operations-virement-delete-dialog.component.html'
})
export class OperationsVirementDeleteDialogComponent {

    operationsVirement: OperationsVirement;

    constructor(
        private operationsVirementService: OperationsVirementService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.operationsVirementService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'operationsVirementListModification',
                content: 'Deleted an operationsVirement'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-operations-virement-delete-popup',
    template: ''
})
export class OperationsVirementDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private operationsVirementPopupService: OperationsVirementPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.operationsVirementPopupService
                .open(OperationsVirementDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
