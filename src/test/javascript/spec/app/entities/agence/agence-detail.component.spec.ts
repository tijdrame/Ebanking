/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { EbankingTestModule } from '../../../test.module';
import { AgenceDetailComponent } from '../../../../../../main/webapp/app/entities/agence/agence-detail.component';
import { AgenceService } from '../../../../../../main/webapp/app/entities/agence/agence.service';
import { Agence } from '../../../../../../main/webapp/app/entities/agence/agence.model';

describe('Component Tests', () => {

    describe('Agence Management Detail Component', () => {
        let comp: AgenceDetailComponent;
        let fixture: ComponentFixture<AgenceDetailComponent>;
        let service: AgenceService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EbankingTestModule],
                declarations: [AgenceDetailComponent],
                providers: [
                    AgenceService
                ]
            })
            .overrideTemplate(AgenceDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AgenceDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AgenceService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Agence(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.agence).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
