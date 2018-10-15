/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { EbankingTestModule } from '../../../test.module';
import { BeneficiaireDetailComponent } from '../../../../../../main/webapp/app/entities/beneficiaire/beneficiaire-detail.component';
import { BeneficiaireService } from '../../../../../../main/webapp/app/entities/beneficiaire/beneficiaire.service';
import { Beneficiaire } from '../../../../../../main/webapp/app/entities/beneficiaire/beneficiaire.model';

describe('Component Tests', () => {

    describe('Beneficiaire Management Detail Component', () => {
        let comp: BeneficiaireDetailComponent;
        let fixture: ComponentFixture<BeneficiaireDetailComponent>;
        let service: BeneficiaireService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EbankingTestModule],
                declarations: [BeneficiaireDetailComponent],
                providers: [
                    BeneficiaireService
                ]
            })
            .overrideTemplate(BeneficiaireDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BeneficiaireDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BeneficiaireService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Beneficiaire(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.beneficiaire).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
