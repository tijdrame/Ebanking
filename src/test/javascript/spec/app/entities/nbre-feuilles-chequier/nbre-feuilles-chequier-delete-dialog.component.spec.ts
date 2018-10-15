/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { EbankingTestModule } from '../../../test.module';
import { NbreFeuillesChequierDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/nbre-feuilles-chequier/nbre-feuilles-chequier-delete-dialog.component';
import { NbreFeuillesChequierService } from '../../../../../../main/webapp/app/entities/nbre-feuilles-chequier/nbre-feuilles-chequier.service';

describe('Component Tests', () => {

    describe('NbreFeuillesChequier Management Delete Component', () => {
        let comp: NbreFeuillesChequierDeleteDialogComponent;
        let fixture: ComponentFixture<NbreFeuillesChequierDeleteDialogComponent>;
        let service: NbreFeuillesChequierService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EbankingTestModule],
                declarations: [NbreFeuillesChequierDeleteDialogComponent],
                providers: [
                    NbreFeuillesChequierService
                ]
            })
            .overrideTemplate(NbreFeuillesChequierDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(NbreFeuillesChequierDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NbreFeuillesChequierService);
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
