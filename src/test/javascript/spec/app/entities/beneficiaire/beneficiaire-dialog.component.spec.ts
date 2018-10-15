/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { EbankingTestModule } from '../../../test.module';
import { BeneficiaireDialogComponent } from '../../../../../../main/webapp/app/entities/beneficiaire/beneficiaire-dialog.component';
import { BeneficiaireService } from '../../../../../../main/webapp/app/entities/beneficiaire/beneficiaire.service';
import { Beneficiaire } from '../../../../../../main/webapp/app/entities/beneficiaire/beneficiaire.model';
import { AbonneService } from '../../../../../../main/webapp/app/entities/abonne';
import { StatutService } from '../../../../../../main/webapp/app/entities/statut';
import { BanquesPartenairesService } from '../../../../../../main/webapp/app/entities/banques-partenaires';

describe('Component Tests', () => {

    describe('Beneficiaire Management Dialog Component', () => {
        let comp: BeneficiaireDialogComponent;
        let fixture: ComponentFixture<BeneficiaireDialogComponent>;
        let service: BeneficiaireService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EbankingTestModule],
                declarations: [BeneficiaireDialogComponent],
                providers: [
                    AbonneService,
                    StatutService,
                    BanquesPartenairesService,
                    BeneficiaireService
                ]
            })
            .overrideTemplate(BeneficiaireDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BeneficiaireDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BeneficiaireService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Beneficiaire(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.beneficiaire = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'beneficiaireListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Beneficiaire();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.beneficiaire = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'beneficiaireListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
