/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { EbankingTestModule } from '../../../test.module';
import { NbreFeuillesChequierDialogComponent } from '../../../../../../main/webapp/app/entities/nbre-feuilles-chequier/nbre-feuilles-chequier-dialog.component';
import { NbreFeuillesChequierService } from '../../../../../../main/webapp/app/entities/nbre-feuilles-chequier/nbre-feuilles-chequier.service';
import { NbreFeuillesChequier } from '../../../../../../main/webapp/app/entities/nbre-feuilles-chequier/nbre-feuilles-chequier.model';

describe('Component Tests', () => {

    describe('NbreFeuillesChequier Management Dialog Component', () => {
        let comp: NbreFeuillesChequierDialogComponent;
        let fixture: ComponentFixture<NbreFeuillesChequierDialogComponent>;
        let service: NbreFeuillesChequierService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EbankingTestModule],
                declarations: [NbreFeuillesChequierDialogComponent],
                providers: [
                    NbreFeuillesChequierService
                ]
            })
            .overrideTemplate(NbreFeuillesChequierDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(NbreFeuillesChequierDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NbreFeuillesChequierService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new NbreFeuillesChequier(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.nbreFeuillesChequier = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'nbreFeuillesChequierListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new NbreFeuillesChequier();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.nbreFeuillesChequier = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'nbreFeuillesChequierListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
