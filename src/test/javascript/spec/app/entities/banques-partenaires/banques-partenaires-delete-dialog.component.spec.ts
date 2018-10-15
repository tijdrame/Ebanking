/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { EbankingTestModule } from '../../../test.module';
import { BanquesPartenairesDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/banques-partenaires/banques-partenaires-delete-dialog.component';
import { BanquesPartenairesService } from '../../../../../../main/webapp/app/entities/banques-partenaires/banques-partenaires.service';

describe('Component Tests', () => {

    describe('BanquesPartenaires Management Delete Component', () => {
        let comp: BanquesPartenairesDeleteDialogComponent;
        let fixture: ComponentFixture<BanquesPartenairesDeleteDialogComponent>;
        let service: BanquesPartenairesService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EbankingTestModule],
                declarations: [BanquesPartenairesDeleteDialogComponent],
                providers: [
                    BanquesPartenairesService
                ]
            })
            .overrideTemplate(BanquesPartenairesDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BanquesPartenairesDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BanquesPartenairesService);
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
