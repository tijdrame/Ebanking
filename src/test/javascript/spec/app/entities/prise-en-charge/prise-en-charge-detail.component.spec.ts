/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { EbankingTestModule } from '../../../test.module';
import { PriseEnChargeDetailComponent } from '../../../../../../main/webapp/app/entities/prise-en-charge/prise-en-charge-detail.component';
import { PriseEnChargeService } from '../../../../../../main/webapp/app/entities/prise-en-charge/prise-en-charge.service';
import { PriseEnCharge } from '../../../../../../main/webapp/app/entities/prise-en-charge/prise-en-charge.model';

describe('Component Tests', () => {

    describe('PriseEnCharge Management Detail Component', () => {
        let comp: PriseEnChargeDetailComponent;
        let fixture: ComponentFixture<PriseEnChargeDetailComponent>;
        let service: PriseEnChargeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EbankingTestModule],
                declarations: [PriseEnChargeDetailComponent],
                providers: [
                    PriseEnChargeService
                ]
            })
            .overrideTemplate(PriseEnChargeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PriseEnChargeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PriseEnChargeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new PriseEnCharge(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.priseEnCharge).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
