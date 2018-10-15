/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { EbankingTestModule } from '../../../test.module';
import { PriseEnChargeDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/prise-en-charge/prise-en-charge-delete-dialog.component';
import { PriseEnChargeService } from '../../../../../../main/webapp/app/entities/prise-en-charge/prise-en-charge.service';

describe('Component Tests', () => {

    describe('PriseEnCharge Management Delete Component', () => {
        let comp: PriseEnChargeDeleteDialogComponent;
        let fixture: ComponentFixture<PriseEnChargeDeleteDialogComponent>;
        let service: PriseEnChargeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EbankingTestModule],
                declarations: [PriseEnChargeDeleteDialogComponent],
                providers: [
                    PriseEnChargeService
                ]
            })
            .overrideTemplate(PriseEnChargeDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PriseEnChargeDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PriseEnChargeService);
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
