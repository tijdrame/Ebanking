import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { TypeAbonne } from './type-abonne.model';
import { TypeAbonneService } from './type-abonne.service';

@Injectable()
export class TypeAbonnePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private typeAbonneService: TypeAbonneService

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
                this.typeAbonneService.find(id)
                    .subscribe((typeAbonneResponse: HttpResponse<TypeAbonne>) => {
                        const typeAbonne: TypeAbonne = typeAbonneResponse.body;
                        this.ngbModalRef = this.typeAbonneModalRef(component, typeAbonne);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.typeAbonneModalRef(component, new TypeAbonne());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    typeAbonneModalRef(component: Component, typeAbonne: TypeAbonne): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.typeAbonne = typeAbonne;
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
