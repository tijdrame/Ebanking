/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EbankingTestModule } from '../../../test.module';
import { PaiementFactureComponent } from '../../../../../../main/webapp/app/entities/paiement-facture/paiement-facture.component';
import { PaiementFactureService } from '../../../../../../main/webapp/app/entities/paiement-facture/paiement-facture.service';
import { PaiementFacture } from '../../../../../../main/webapp/app/entities/paiement-facture/paiement-facture.model';

describe('Component Tests', () => {

    describe('PaiementFacture Management Component', () => {
        let comp: PaiementFactureComponent;
        let fixture: ComponentFixture<PaiementFactureComponent>;
        let service: PaiementFactureService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EbankingTestModule],
                declarations: [PaiementFactureComponent],
                providers: [
                    PaiementFactureService
                ]
            })
            .overrideTemplate(PaiementFactureComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PaiementFactureComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PaiementFactureService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new PaiementFacture(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.paiementFactures[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
