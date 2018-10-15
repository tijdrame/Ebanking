/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { EbankingTestModule } from '../../../test.module';
import { TypeOppositionDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/type-opposition/type-opposition-delete-dialog.component';
import { TypeOppositionService } from '../../../../../../main/webapp/app/entities/type-opposition/type-opposition.service';

describe('Component Tests', () => {

    describe('TypeOpposition Management Delete Component', () => {
        let comp: TypeOppositionDeleteDialogComponent;
        let fixture: ComponentFixture<TypeOppositionDeleteDialogComponent>;
        let service: TypeOppositionService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EbankingTestModule],
                declarations: [TypeOppositionDeleteDialogComponent],
                providers: [
                    TypeOppositionService
                ]
            })
            .overrideTemplate(TypeOppositionDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TypeOppositionDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TypeOppositionService);
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
