import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { TypeOpposition } from './type-opposition.model';
import { TypeOppositionPopupService } from './type-opposition-popup.service';
import { TypeOppositionService } from './type-opposition.service';

@Component({
    selector: 'jhi-type-opposition-delete-dialog',
    templateUrl: './type-opposition-delete-dialog.component.html'
})
export class TypeOppositionDeleteDialogComponent {

    typeOpposition: TypeOpposition;

    constructor(
        private typeOppositionService: TypeOppositionService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.typeOppositionService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'typeOppositionListModification',
                content: 'Deleted an typeOpposition'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-type-opposition-delete-popup',
    template: ''
})
export class TypeOppositionDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private typeOppositionPopupService: TypeOppositionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.typeOppositionPopupService
                .open(TypeOppositionDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
