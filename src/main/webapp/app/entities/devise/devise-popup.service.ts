import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { Devise } from './devise.model';
import { DeviseService } from './devise.service';

@Injectable()
export class DevisePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private deviseService: DeviseService

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
                this.deviseService.find(id)
                    .subscribe((deviseResponse: HttpResponse<Devise>) => {
                        const devise: Devise = deviseResponse.body;
                        if (devise.dateMaj) {
                            devise.dateMaj = {
                                year: devise.dateMaj.getFullYear(),
                                month: devise.dateMaj.getMonth() + 1,
                                day: devise.dateMaj.getDate()
                            };
                        }
                        this.ngbModalRef = this.deviseModalRef(component, devise);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.deviseModalRef(component, new Devise());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    deviseModalRef(component: Component, devise: Devise): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.devise = devise;
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
