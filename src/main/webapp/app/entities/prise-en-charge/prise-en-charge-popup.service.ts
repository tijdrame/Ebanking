import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { PriseEnCharge } from './prise-en-charge.model';
import { PriseEnChargeService } from './prise-en-charge.service';

@Injectable()
export class PriseEnChargePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private priseEnChargeService: PriseEnChargeService

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
                this.priseEnChargeService.find(id)
                    .subscribe((priseEnChargeResponse: HttpResponse<PriseEnCharge>) => {
                        const priseEnCharge: PriseEnCharge = priseEnChargeResponse.body;
                        this.ngbModalRef = this.priseEnChargeModalRef(component, priseEnCharge);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.priseEnChargeModalRef(component, new PriseEnCharge());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    priseEnChargeModalRef(component: Component, priseEnCharge: PriseEnCharge): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.priseEnCharge = priseEnCharge;
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
