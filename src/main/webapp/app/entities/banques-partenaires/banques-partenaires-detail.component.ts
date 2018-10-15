import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { BanquesPartenaires } from './banques-partenaires.model';
import { BanquesPartenairesService } from './banques-partenaires.service';

@Component({
    selector: 'jhi-banques-partenaires-detail',
    templateUrl: './banques-partenaires-detail.component.html'
})
export class BanquesPartenairesDetailComponent implements OnInit, OnDestroy {

    banquesPartenaires: BanquesPartenaires;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private banquesPartenairesService: BanquesPartenairesService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInBanquesPartenaires();
    }

    load(id) {
        this.banquesPartenairesService.find(id)
            .subscribe((banquesPartenairesResponse: HttpResponse<BanquesPartenaires>) => {
                this.banquesPartenaires = banquesPartenairesResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInBanquesPartenaires() {
        this.eventSubscriber = this.eventManager.subscribe(
            'banquesPartenairesListModification',
            (response) => this.load(this.banquesPartenaires.id)
        );
    }
}
