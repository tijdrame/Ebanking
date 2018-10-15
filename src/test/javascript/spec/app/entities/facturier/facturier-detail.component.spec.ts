/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { EbankingTestModule } from '../../../test.module';
import { FacturierDetailComponent } from '../../../../../../main/webapp/app/entities/facturier/facturier-detail.component';
import { FacturierService } from '../../../../../../main/webapp/app/entities/facturier/facturier.service';
import { Facturier } from '../../../../../../main/webapp/app/entities/facturier/facturier.model';

describe('Component Tests', () => {

    describe('Facturier Management Detail Component', () => {
        let comp: FacturierDetailComponent;
        let fixture: ComponentFixture<FacturierDetailComponent>;
        let service: FacturierService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EbankingTestModule],
                declarations: [FacturierDetailComponent],
                providers: [
                    FacturierService
                ]
            })
            .overrideTemplate(FacturierDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FacturierDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FacturierService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Facturier(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.facturier).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
