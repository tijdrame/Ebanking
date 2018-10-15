import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { NbreFeuillesChequier } from './nbre-feuilles-chequier.model';
import { NbreFeuillesChequierPopupService } from './nbre-feuilles-chequier-popup.service';
import { NbreFeuillesChequierService } from './nbre-feuilles-chequier.service';

@Component({
    selector: 'jhi-nbre-feuilles-chequier-delete-dialog',
    templateUrl: './nbre-feuilles-chequier-delete-dialog.component.html'
})
export class NbreFeuillesChequierDeleteDialogComponent {

    nbreFeuillesChequier: NbreFeuillesChequier;

    constructor(
        private nbreFeuillesChequierService: NbreFeuillesChequierService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.nbreFeuillesChequierService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'nbreFeuillesChequierListModification',
                content: 'Deleted an nbreFeuillesChequier'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-nbre-feuilles-chequier-delete-popup',
    template: ''
})
export class NbreFeuillesChequierDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private nbreFeuillesChequierPopupService: NbreFeuillesChequierPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.nbreFeuillesChequierPopupService
                .open(NbreFeuillesChequierDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
