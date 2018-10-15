/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { EbankingTestModule } from '../../../test.module';
import { DeviseDetailComponent } from '../../../../../../main/webapp/app/entities/devise/devise-detail.component';
import { DeviseService } from '../../../../../../main/webapp/app/entities/devise/devise.service';
import { Devise } from '../../../../../../main/webapp/app/entities/devise/devise.model';

describe('Component Tests', () => {

    describe('Devise Management Detail Component', () => {
        let comp: DeviseDetailComponent;
        let fixture: ComponentFixture<DeviseDetailComponent>;
        let service: DeviseService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EbankingTestModule],
                declarations: [DeviseDetailComponent],
                providers: [
                    DeviseService
                ]
            })
            .overrideTemplate(DeviseDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DeviseDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DeviseService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Devise(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.devise).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
