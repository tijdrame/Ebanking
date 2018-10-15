import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { NbreFeuillesChequier } from './nbre-feuilles-chequier.model';
import { NbreFeuillesChequierService } from './nbre-feuilles-chequier.service';

@Component({
    selector: 'jhi-nbre-feuilles-chequier-detail',
    templateUrl: './nbre-feuilles-chequier-detail.component.html'
})
export class NbreFeuillesChequierDetailComponent implements OnInit, OnDestroy {

    nbreFeuillesChequier: NbreFeuillesChequier;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private nbreFeuillesChequierService: NbreFeuillesChequierService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInNbreFeuillesChequiers();
    }

    load(id) {
        this.nbreFeuillesChequierService.find(id)
            .subscribe((nbreFeuillesChequierResponse: HttpResponse<NbreFeuillesChequier>) => {
                this.nbreFeuillesChequier = nbreFeuillesChequierResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInNbreFeuillesChequiers() {
        this.eventSubscriber = this.eventManager.subscribe(
            'nbreFeuillesChequierListModification',
            (response) => this.load(this.nbreFeuillesChequier.id)
        );
    }
}
