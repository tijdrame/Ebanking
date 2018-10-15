/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EbankingTestModule } from '../../../test.module';
import { BeneficiaireComponent } from '../../../../../../main/webapp/app/entities/beneficiaire/beneficiaire.component';
import { BeneficiaireService } from '../../../../../../main/webapp/app/entities/beneficiaire/beneficiaire.service';
import { Beneficiaire } from '../../../../../../main/webapp/app/entities/beneficiaire/beneficiaire.model';

describe('Component Tests', () => {

    describe('Beneficiaire Management Component', () => {
        let comp: BeneficiaireComponent;
        let fixture: ComponentFixture<BeneficiaireComponent>;
        let service: BeneficiaireService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EbankingTestModule],
                declarations: [BeneficiaireComponent],
                providers: [
                    BeneficiaireService
                ]
            })
            .overrideTemplate(BeneficiaireComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BeneficiaireComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BeneficiaireService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Beneficiaire(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.beneficiaires[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
