import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { TypeOpposition } from './type-opposition.model';
import { TypeOppositionService } from './type-opposition.service';

@Injectable()
export class TypeOppositionPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private typeOppositionService: TypeOppositionService

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
                this.typeOppositionService.find(id)
                    .subscribe((typeOppositionResponse: HttpResponse<TypeOpposition>) => {
                        const typeOpposition: TypeOpposition = typeOppositionResponse.body;
                        this.ngbModalRef = this.typeOppositionModalRef(component, typeOpposition);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.typeOppositionModalRef(component, new TypeOpposition());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    typeOppositionModalRef(component: Component, typeOpposition: TypeOpposition): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.typeOpposition = typeOpposition;
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
