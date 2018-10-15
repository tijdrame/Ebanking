/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { EbankingTestModule } from '../../../test.module';
import { OperationsVirementDialogComponent } from '../../../../../../main/webapp/app/entities/operations-virement/operations-virement-dialog.component';
import { OperationsVirementService } from '../../../../../../main/webapp/app/entities/operations-virement/operations-virement.service';
import { OperationsVirement } from '../../../../../../main/webapp/app/entities/operations-virement/operations-virement.model';
import { BeneficiaireService } from '../../../../../../main/webapp/app/entities/beneficiaire';
import { DeviseService } from '../../../../../../main/webapp/app/entities/devise';
import { StatutService } from '../../../../../../main/webapp/app/entities/statut';
import { OperationService } from '../../../../../../main/webapp/app/entities/operation';

describe('Component Tests', () => {

    describe('OperationsVirement Management Dialog Component', () => {
        let comp: OperationsVirementDialogComponent;
        let fixture: ComponentFixture<OperationsVirementDialogComponent>;
        let service: OperationsVirementService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EbankingTestModule],
                declarations: [OperationsVirementDialogComponent],
                providers: [
                    BeneficiaireService,
                    DeviseService,
                    StatutService,
                    OperationService,
                    OperationsVirementService
                ]
            })
            .overrideTemplate(OperationsVirementDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OperationsVirementDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OperationsVirementService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new OperationsVirement(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.operationsVirement = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'operationsVirementListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new OperationsVirement();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.operationsVirement = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'operationsVirementListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
