import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { TypeCarte } from './type-carte.model';
import { TypeCartePopupService } from './type-carte-popup.service';
import { TypeCarteService } from './type-carte.service';

@Component({
    selector: 'jhi-type-carte-delete-dialog',
    templateUrl: './type-carte-delete-dialog.component.html'
})
export class TypeCarteDeleteDialogComponent {

    typeCarte: TypeCarte;

    constructor(
        private typeCarteService: TypeCarteService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.typeCarteService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'typeCarteListModification',
                content: 'Deleted an typeCarte'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-type-carte-delete-popup',
    template: ''
})
export class TypeCarteDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private typeCartePopupService: TypeCartePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.typeCartePopupService
                .open(TypeCarteDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
