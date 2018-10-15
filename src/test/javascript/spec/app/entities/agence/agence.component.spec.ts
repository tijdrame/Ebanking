/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EbankingTestModule } from '../../../test.module';
import { AgenceComponent } from '../../../../../../main/webapp/app/entities/agence/agence.component';
import { AgenceService } from '../../../../../../main/webapp/app/entities/agence/agence.service';
import { Agence } from '../../../../../../main/webapp/app/entities/agence/agence.model';

describe('Component Tests', () => {

    describe('Agence Management Component', () => {
        let comp: AgenceComponent;
        let fixture: ComponentFixture<AgenceComponent>;
        let service: AgenceService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EbankingTestModule],
                declarations: [AgenceComponent],
                providers: [
                    AgenceService
                ]
            })
            .overrideTemplate(AgenceComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AgenceComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AgenceService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Agence(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.agences[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
