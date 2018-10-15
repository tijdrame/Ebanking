/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { EbankingTestModule } from '../../../test.module';
import { AbonneDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/abonne/abonne-delete-dialog.component';
import { AbonneService } from '../../../../../../main/webapp/app/entities/abonne/abonne.service';

describe('Component Tests', () => {

    describe('Abonne Management Delete Component', () => {
        let comp: AbonneDeleteDialogComponent;
        let fixture: ComponentFixture<AbonneDeleteDialogComponent>;
        let service: AbonneService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EbankingTestModule],
                declarations: [AbonneDeleteDialogComponent],
                providers: [
                    AbonneService
                ]
            })
            .overrideTemplate(AbonneDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AbonneDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AbonneService);
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
