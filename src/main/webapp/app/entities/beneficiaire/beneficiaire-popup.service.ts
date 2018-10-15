import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { Beneficiaire } from './beneficiaire.model';
import { BeneficiaireService } from './beneficiaire.service';

@Injectable()
export class BeneficiairePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private beneficiaireService: BeneficiaireService

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
                this.beneficiaireService.find(id)
                    .subscribe((beneficiaireResponse: HttpResponse<Beneficiaire>) => {
                        const beneficiaire: Beneficiaire = beneficiaireResponse.body;
                        if (beneficiaire.dateDemande) {
                            beneficiaire.dateDemande = {
                                year: beneficiaire.dateDemande.getFullYear(),
                                month: beneficiaire.dateDemande.getMonth() + 1,
                                day: beneficiaire.dateDemande.getDate()
                            };
                        }
                        if (beneficiaire.dateAcceptation) {
                            beneficiaire.dateAcceptation = {
                                year: beneficiaire.dateAcceptation.getFullYear(),
                                month: beneficiaire.dateAcceptation.getMonth() + 1,
                                day: beneficiaire.dateAcceptation.getDate()
                            };
                        }
                        this.ngbModalRef = this.beneficiaireModalRef(component, beneficiaire);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.beneficiaireModalRef(component, new Beneficiaire());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    beneficiaireModalRef(component: Component, beneficiaire: Beneficiaire): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.beneficiaire = beneficiaire;
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
