/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EbankingTestModule } from '../../../test.module';
import { FacturierComponent } from '../../../../../../main/webapp/app/entities/facturier/facturier.component';
import { FacturierService } from '../../../../../../main/webapp/app/entities/facturier/facturier.service';
import { Facturier } from '../../../../../../main/webapp/app/entities/facturier/facturier.model';

describe('Component Tests', () => {

    describe('Facturier Management Component', () => {
        let comp: FacturierComponent;
        let fixture: ComponentFixture<FacturierComponent>;
        let service: FacturierService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EbankingTestModule],
                declarations: [FacturierComponent],
                providers: [
                    FacturierService
                ]
            })
            .overrideTemplate(FacturierComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FacturierComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FacturierService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Facturier(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.facturiers[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
