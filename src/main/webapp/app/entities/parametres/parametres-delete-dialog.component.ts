import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Parametres } from './parametres.model';
import { ParametresPopupService } from './parametres-popup.service';
import { ParametresService } from './parametres.service';

@Component({
    selector: 'jhi-parametres-delete-dialog',
    templateUrl: './parametres-delete-dialog.component.html'
})
export class ParametresDeleteDialogComponent {

    parametres: Parametres;

    constructor(
        private parametresService: ParametresService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.parametresService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'parametresListModification',
                content: 'Deleted an parametres'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-parametres-delete-popup',
    template: ''
})
export class ParametresDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private parametresPopupService: ParametresPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.parametresPopupService
                .open(ParametresDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
