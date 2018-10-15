import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { TypeChequier } from './type-chequier.model';
import { TypeChequierPopupService } from './type-chequier-popup.service';
import { TypeChequierService } from './type-chequier.service';

@Component({
    selector: 'jhi-type-chequier-delete-dialog',
    templateUrl: './type-chequier-delete-dialog.component.html'
})
export class TypeChequierDeleteDialogComponent {

    typeChequier: TypeChequier;

    constructor(
        private typeChequierService: TypeChequierService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.typeChequierService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'typeChequierListModification',
                content: 'Deleted an typeChequier'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-type-chequier-delete-popup',
    template: ''
})
export class TypeChequierDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private typeChequierPopupService: TypeChequierPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.typeChequierPopupService
                .open(TypeChequierDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
