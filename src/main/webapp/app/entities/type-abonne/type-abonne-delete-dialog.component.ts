import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { TypeAbonne } from './type-abonne.model';
import { TypeAbonnePopupService } from './type-abonne-popup.service';
import { TypeAbonneService } from './type-abonne.service';

@Component({
    selector: 'jhi-type-abonne-delete-dialog',
    templateUrl: './type-abonne-delete-dialog.component.html'
})
export class TypeAbonneDeleteDialogComponent {

    typeAbonne: TypeAbonne;

    constructor(
        private typeAbonneService: TypeAbonneService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.typeAbonneService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'typeAbonneListModification',
                content: 'Deleted an typeAbonne'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-type-abonne-delete-popup',
    template: ''
})
export class TypeAbonneDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private typeAbonnePopupService: TypeAbonnePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.typeAbonnePopupService
                .open(TypeAbonneDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
