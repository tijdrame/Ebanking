import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { PaiementFacture } from './paiement-facture.model';
import { PaiementFactureService } from './paiement-facture.service';

@Injectable()
export class PaiementFacturePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private paiementFactureService: PaiementFactureService

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
                this.paiementFactureService.find(id)
                    .subscribe((paiementFactureResponse: HttpResponse<PaiementFacture>) => {
                        const paiementFacture: PaiementFacture = paiementFactureResponse.body;
                        if (paiementFacture.dateDebut) {
                            paiementFacture.dateDebut = {
                                year: paiementFacture.dateDebut.getFullYear(),
                                month: paiementFacture.dateDebut.getMonth() + 1,
                                day: paiementFacture.dateDebut.getDate()
                            };
                        }
                        if (paiementFacture.dateFin) {
                            paiementFacture.dateFin = {
                                year: paiementFacture.dateFin.getFullYear(),
                                month: paiementFacture.dateFin.getMonth() + 1,
                                day: paiementFacture.dateFin.getDate()
                            };
                        }
                        if (paiementFacture.dateAcceptation) {
                            paiementFacture.dateAcceptation = {
                                year: paiementFacture.dateAcceptation.getFullYear(),
                                month: paiementFacture.dateAcceptation.getMonth() + 1,
                                day: paiementFacture.dateAcceptation.getDate()
                            };
                        }
                        if (paiementFacture.dateDemande) {
                            paiementFacture.dateDemande = {
                                year: paiementFacture.dateDemande.getFullYear(),
                                month: paiementFacture.dateDemande.getMonth() + 1,
                                day: paiementFacture.dateDemande.getDate()
                            };
                        }
                        this.ngbModalRef = this.paiementFactureModalRef(component, paiementFacture);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.paiementFactureModalRef(component, new PaiementFacture());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    paiementFactureModalRef(component: Component, paiementFacture: PaiementFacture): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.paiementFacture = paiementFacture;
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
