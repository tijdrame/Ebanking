import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { TypeCompte } from './type-compte.model';
import { TypeComptePopupService } from './type-compte-popup.service';
import { TypeCompteService } from './type-compte.service';

@Component({
    selector: 'jhi-type-compte-delete-dialog',
    templateUrl: './type-compte-delete-dialog.component.html'
})
export class TypeCompteDeleteDialogComponent {

    typeCompte: TypeCompte;

    constructor(
        private typeCompteService: TypeCompteService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.typeCompteService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'typeCompteListModification',
                content: 'Deleted an typeCompte'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-type-compte-delete-popup',
    template: ''
})
export class TypeCompteDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private typeComptePopupService: TypeComptePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.typeComptePopupService
                .open(TypeCompteDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
