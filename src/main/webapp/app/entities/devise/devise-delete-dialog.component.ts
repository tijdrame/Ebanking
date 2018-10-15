import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Devise } from './devise.model';
import { DevisePopupService } from './devise-popup.service';
import { DeviseService } from './devise.service';

@Component({
    selector: 'jhi-devise-delete-dialog',
    templateUrl: './devise-delete-dialog.component.html'
})
export class DeviseDeleteDialogComponent {

    devise: Devise;

    constructor(
        private deviseService: DeviseService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.deviseService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'deviseListModification',
                content: 'Deleted an devise'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-devise-delete-popup',
    template: ''
})
export class DeviseDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private devisePopupService: DevisePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.devisePopupService
                .open(DeviseDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
