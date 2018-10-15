/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EbankingTestModule } from '../../../test.module';
import { PriseEnChargeComponent } from '../../../../../../main/webapp/app/entities/prise-en-charge/prise-en-charge.component';
import { PriseEnChargeService } from '../../../../../../main/webapp/app/entities/prise-en-charge/prise-en-charge.service';
import { PriseEnCharge } from '../../../../../../main/webapp/app/entities/prise-en-charge/prise-en-charge.model';

describe('Component Tests', () => {

    describe('PriseEnCharge Management Component', () => {
        let comp: PriseEnChargeComponent;
        let fixture: ComponentFixture<PriseEnChargeComponent>;
        let service: PriseEnChargeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EbankingTestModule],
                declarations: [PriseEnChargeComponent],
                providers: [
                    PriseEnChargeService
                ]
            })
            .overrideTemplate(PriseEnChargeComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PriseEnChargeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PriseEnChargeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new PriseEnCharge(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.priseEnCharges[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
