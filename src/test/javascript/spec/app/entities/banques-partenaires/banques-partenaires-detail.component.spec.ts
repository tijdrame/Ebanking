/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { EbankingTestModule } from '../../../test.module';
import { BanquesPartenairesDetailComponent } from '../../../../../../main/webapp/app/entities/banques-partenaires/banques-partenaires-detail.component';
import { BanquesPartenairesService } from '../../../../../../main/webapp/app/entities/banques-partenaires/banques-partenaires.service';
import { BanquesPartenaires } from '../../../../../../main/webapp/app/entities/banques-partenaires/banques-partenaires.model';

describe('Component Tests', () => {

    describe('BanquesPartenaires Management Detail Component', () => {
        let comp: BanquesPartenairesDetailComponent;
        let fixture: ComponentFixture<BanquesPartenairesDetailComponent>;
        let service: BanquesPartenairesService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EbankingTestModule],
                declarations: [BanquesPartenairesDetailComponent],
                providers: [
                    BanquesPartenairesService
                ]
            })
            .overrideTemplate(BanquesPartenairesDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BanquesPartenairesDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BanquesPartenairesService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new BanquesPartenaires(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.banquesPartenaires).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
