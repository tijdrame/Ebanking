/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { EbankingTestModule } from '../../../test.module';
import { ParametresDetailComponent } from '../../../../../../main/webapp/app/entities/parametres/parametres-detail.component';
import { ParametresService } from '../../../../../../main/webapp/app/entities/parametres/parametres.service';
import { Parametres } from '../../../../../../main/webapp/app/entities/parametres/parametres.model';

describe('Component Tests', () => {

    describe('Parametres Management Detail Component', () => {
        let comp: ParametresDetailComponent;
        let fixture: ComponentFixture<ParametresDetailComponent>;
        let service: ParametresService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EbankingTestModule],
                declarations: [ParametresDetailComponent],
                providers: [
                    ParametresService
                ]
            })
            .overrideTemplate(ParametresDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ParametresDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ParametresService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Parametres(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.parametres).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
