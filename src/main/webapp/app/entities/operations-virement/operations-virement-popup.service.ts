import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { OperationsVirement } from './operations-virement.model';
import { OperationsVirementService } from './operations-virement.service';

@Injectable()
export class OperationsVirementPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private operationsVirementService: OperationsVirementService

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
                this.operationsVirementService.find(id)
                    .subscribe((operationsVirementResponse: HttpResponse<OperationsVirement>) => {
                        const operationsVirement: OperationsVirement = operationsVirementResponse.body;
                        this.ngbModalRef = this.operationsVirementModalRef(component, operationsVirement);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.operationsVirementModalRef(component, new OperationsVirement());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    operationsVirementModalRef(component: Component, operationsVirement: OperationsVirement): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.operationsVirement = operationsVirement;
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
