/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { EbankingTestModule } from '../../../test.module';
import { BeneficiaireDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/beneficiaire/beneficiaire-delete-dialog.component';
import { BeneficiaireService } from '../../../../../../main/webapp/app/entities/beneficiaire/beneficiaire.service';

describe('Component Tests', () => {

    describe('Beneficiaire Management Delete Component', () => {
        let comp: BeneficiaireDeleteDialogComponent;
        let fixture: ComponentFixture<BeneficiaireDeleteDialogComponent>;
        let service: BeneficiaireService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EbankingTestModule],
                declarations: [BeneficiaireDeleteDialogComponent],
                providers: [
                    BeneficiaireService
                ]
            })
            .overrideTemplate(BeneficiaireDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BeneficiaireDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BeneficiaireService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
