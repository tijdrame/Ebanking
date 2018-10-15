/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EbankingTestModule } from '../../../test.module';
import { LogEvenementComponent } from '../../../../../../main/webapp/app/entities/log-evenement/log-evenement.component';
import { LogEvenementService } from '../../../../../../main/webapp/app/entities/log-evenement/log-evenement.service';
import { LogEvenement } from '../../../../../../main/webapp/app/entities/log-evenement/log-evenement.model';

describe('Component Tests', () => {

    describe('LogEvenement Management Component', () => {
        let comp: LogEvenementComponent;
        let fixture: ComponentFixture<LogEvenementComponent>;
        let service: LogEvenementService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EbankingTestModule],
                declarations: [LogEvenementComponent],
                providers: [
                    LogEvenementService
                ]
            })
            .overrideTemplate(LogEvenementComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LogEvenementComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LogEvenementService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new LogEvenement(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.logEvenements[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
