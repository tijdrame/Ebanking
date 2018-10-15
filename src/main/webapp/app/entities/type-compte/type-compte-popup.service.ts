import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { TypeCompte } from './type-compte.model';
import { TypeCompteService } from './type-compte.service';

@Injectable()
export class TypeComptePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private typeCompteService: TypeCompteService

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
                this.typeCompteService.find(id)
                    .subscribe((typeCompteResponse: HttpResponse<TypeCompte>) => {
                        const typeCompte: TypeCompte = typeCompteResponse.body;
                        this.ngbModalRef = this.typeCompteModalRef(component, typeCompte);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.typeCompteModalRef(component, new TypeCompte());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    typeCompteModalRef(component: Component, typeCompte: TypeCompte): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.typeCompte = typeCompte;
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
