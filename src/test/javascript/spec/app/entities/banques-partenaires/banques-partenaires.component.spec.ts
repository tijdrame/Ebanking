/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EbankingTestModule } from '../../../test.module';
import { BanquesPartenairesComponent } from '../../../../../../main/webapp/app/entities/banques-partenaires/banques-partenaires.component';
import { BanquesPartenairesService } from '../../../../../../main/webapp/app/entities/banques-partenaires/banques-partenaires.service';
import { BanquesPartenaires } from '../../../../../../main/webapp/app/entities/banques-partenaires/banques-partenaires.model';

describe('Component Tests', () => {

    describe('BanquesPartenaires Management Component', () => {
        let comp: BanquesPartenairesComponent;
        let fixture: ComponentFixture<BanquesPartenairesComponent>;
        let service: BanquesPartenairesService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EbankingTestModule],
                declarations: [BanquesPartenairesComponent],
                providers: [
                    BanquesPartenairesService
                ]
            })
            .overrideTemplate(BanquesPartenairesComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BanquesPartenairesComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BanquesPartenairesService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new BanquesPartenaires(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.banquesPartenaires[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
