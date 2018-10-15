import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { NbreFeuillesChequier } from './nbre-feuilles-chequier.model';
import { NbreFeuillesChequierService } from './nbre-feuilles-chequier.service';

@Injectable()
export class NbreFeuillesChequierPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private nbreFeuillesChequierService: NbreFeuillesChequierService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.nbreFeuillesChequierService.find(id)
                    .subscribe((nbreFeuillesChequierResponse: HttpResponse<NbreFeuillesChequier>) => {
                        const nbreFeuillesChequier: NbreFeuillesChequier = nbreFeuillesChequierResponse.body;
                        this.ngbModalRef = this.nbreFeuillesChequierModalRef(component, nbreFeuillesChequier);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.nbreFeuillesChequierModalRef(component, new NbreFeuillesChequier());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    nbreFeuillesChequierModalRef(component: Component, nbreFeuillesChequier: NbreFeuillesChequier): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.nbreFeuillesChequier = nbreFeuillesChequier;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
