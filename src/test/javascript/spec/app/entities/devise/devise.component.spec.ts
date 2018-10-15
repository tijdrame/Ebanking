/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EbankingTestModule } from '../../../test.module';
import { DeviseComponent } from '../../../../../../main/webapp/app/entities/devise/devise.component';
import { DeviseService } from '../../../../../../main/webapp/app/entities/devise/devise.service';
import { Devise } from '../../../../../../main/webapp/app/entities/devise/devise.model';

describe('Component Tests', () => {

    describe('Devise Management Component', () => {
        let comp: DeviseComponent;
        let fixture: ComponentFixture<DeviseComponent>;
        let service: DeviseService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EbankingTestModule],
                declarations: [DeviseComponent],
                providers: [
                    DeviseService
                ]
            })
            .overrideTemplate(DeviseComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DeviseComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DeviseService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Devise(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.devises[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
