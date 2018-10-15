/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { EbankingTestModule } from '../../../test.module';
import { PaiementFactureDialogComponent } from '../../../../../../main/webapp/app/entities/paiement-facture/paiement-facture-dialog.component';
import { PaiementFactureService } from '../../../../../../main/webapp/app/entities/paiement-facture/paiement-facture.service';
import { PaiementFacture } from '../../../../../../main/webapp/app/entities/paiement-facture/paiement-facture.model';
import { DeviseService } from '../../../../../../main/webapp/app/entities/devise';
import { CompteService } from '../../../../../../main/webapp/app/entities/compte';
import { FacturierService } from '../../../../../../main/webapp/app/entities/facturier';
import { StatutService } from '../../../../../../main/webapp/app/entities/statut';

describe('Component Tests', () => {

    describe('PaiementFacture Management Dialog Component', () => {
        let comp: PaiementFactureDialogComponent;
        let fixture: ComponentFixture<PaiementFactureDialogComponent>;
        let service: PaiementFactureService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EbankingTestModule],
                declarations: [PaiementFactureDialogComponent],
                providers: [
                    DeviseService,
                    CompteService,
                    FacturierService,
                    StatutService,
                    PaiementFactureService
                ]
            })
            .overrideTemplate(PaiementFactureDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PaiementFactureDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PaiementFactureService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new PaiementFacture(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.paiementFacture = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'paiementFactureListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new PaiementFacture();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.paiementFacture = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'paiementFactureListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
