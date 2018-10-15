import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Abonne } from './abonne.model';
import { AbonnePopupService } from './abonne-popup.service';
import { AbonneService } from './abonne.service';

@Component({
    selector: 'jhi-abonne-delete-dialog',
    templateUrl: './abonne-delete-dialog.component.html'
})
export class AbonneDeleteDialogComponent {

    abonne: Abonne;

    constructor(
        private abonneService: AbonneService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.abonneService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'abonneListModification',
                content: 'Deleted an abonne'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-abonne-delete-popup',
    template: ''
})
export class AbonneDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private abonnePopupService: AbonnePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.abonnePopupService
                .open(AbonneDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
