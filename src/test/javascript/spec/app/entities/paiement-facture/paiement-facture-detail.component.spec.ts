/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { EbankingTestModule } from '../../../test.module';
import { PaiementFactureDetailComponent } from '../../../../../../main/webapp/app/entities/paiement-facture/paiement-facture-detail.component';
import { PaiementFactureService } from '../../../../../../main/webapp/app/entities/paiement-facture/paiement-facture.service';
import { PaiementFacture } from '../../../../../../main/webapp/app/entities/paiement-facture/paiement-facture.model';

describe('Component Tests', () => {

    describe('PaiementFacture Management Detail Component', () => {
        let comp: PaiementFactureDetailComponent;
        let fixture: ComponentFixture<PaiementFactureDetailComponent>;
        let service: PaiementFactureService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EbankingTestModule],
                declarations: [PaiementFactureDetailComponent],
                providers: [
                    PaiementFactureService
                ]
            })
            .overrideTemplate(PaiementFactureDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PaiementFactureDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PaiementFactureService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new PaiementFacture(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.paiementFacture).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
