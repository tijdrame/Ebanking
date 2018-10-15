/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { EbankingTestModule } from '../../../test.module';
import { BanquesPartenairesDialogComponent } from '../../../../../../main/webapp/app/entities/banques-partenaires/banques-partenaires-dialog.component';
import { BanquesPartenairesService } from '../../../../../../main/webapp/app/entities/banques-partenaires/banques-partenaires.service';
import { BanquesPartenaires } from '../../../../../../main/webapp/app/entities/banques-partenaires/banques-partenaires.model';

describe('Component Tests', () => {

    describe('BanquesPartenaires Management Dialog Component', () => {
        let comp: BanquesPartenairesDialogComponent;
        let fixture: ComponentFixture<BanquesPartenairesDialogComponent>;
        let service: BanquesPartenairesService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EbankingTestModule],
                declarations: [BanquesPartenairesDialogComponent],
                providers: [
                    BanquesPartenairesService
                ]
            })
            .overrideTemplate(BanquesPartenairesDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BanquesPartenairesDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BanquesPartenairesService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new BanquesPartenaires(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.banquesPartenaires = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'banquesPartenairesListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new BanquesPartenaires();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.banquesPartenaires = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'banquesPartenairesListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});