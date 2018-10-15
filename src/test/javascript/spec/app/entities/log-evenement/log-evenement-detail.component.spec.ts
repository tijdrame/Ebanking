/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { EbankingTestModule } from '../../../test.module';
import { LogEvenementDetailComponent } from '../../../../../../main/webapp/app/entities/log-evenement/log-evenement-detail.component';
import { LogEvenementService } from '../../../../../../main/webapp/app/entities/log-evenement/log-evenement.service';
import { LogEvenement } from '../../../../../../main/webapp/app/entities/log-evenement/log-evenement.model';

describe('Component Tests', () => {

    describe('LogEvenement Management Detail Component', () => {
        let comp: LogEvenementDetailComponent;
        let fixture: ComponentFixture<LogEvenementDetailComponent>;
        let service: LogEvenementService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EbankingTestModule],
                declarations: [LogEvenementDetailComponent],
                providers: [
                    LogEvenementService
                ]
            })
            .overrideTemplate(LogEvenementDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LogEvenementDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LogEvenementService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new LogEvenement(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.logEvenement).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
