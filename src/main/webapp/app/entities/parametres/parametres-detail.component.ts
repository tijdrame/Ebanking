import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Parametres } from './parametres.model';
import { ParametresService } from './parametres.service';

@Component({
    selector: 'jhi-parametres-detail',
    templateUrl: './parametres-detail.component.html'
})
export class ParametresDetailComponent implements OnInit, OnDestroy {

    parametres: Parametres;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private parametresService: ParametresService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInParametres();
    }

    load(id) {
        this.parametresService.find(id)
            .subscribe((parametresResponse: HttpResponse<Parametres>) => {
                this.parametres = parametresResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInParametres() {
        this.eventSubscriber = this.eventManager.subscribe(
            'parametresListModification',
            (response) => this.load(this.parametres.id)
        );
    }
}
