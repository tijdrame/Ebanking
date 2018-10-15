import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { BanquesPartenaires } from './banques-partenaires.model';
import { BanquesPartenairesPopupService } from './banques-partenaires-popup.service';
import { BanquesPartenairesService } from './banques-partenaires.service';

@Component({
    selector: 'jhi-banques-partenaires-delete-dialog',
    templateUrl: './banques-partenaires-delete-dialog.component.html'
})
export class BanquesPartenairesDeleteDialogComponent {

    banquesPartenaires: BanquesPartenaires;

    constructor(
        private banquesPartenairesService: BanquesPartenairesService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.banquesPartenairesService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'banquesPartenairesListModification',
                content: 'Deleted an banquesPartenaires'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-banques-partenaires-delete-popup',
    template: ''
})
export class BanquesPartenairesDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private banquesPartenairesPopupService: BanquesPartenairesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.banquesPartenairesPopupService
                .open(BanquesPartenairesDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
