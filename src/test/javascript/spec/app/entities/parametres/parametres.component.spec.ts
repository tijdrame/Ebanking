/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EbankingTestModule } from '../../../test.module';
import { ParametresComponent } from '../../../../../../main/webapp/app/entities/parametres/parametres.component';
import { ParametresService } from '../../../../../../main/webapp/app/entities/parametres/parametres.service';
import { Parametres } from '../../../../../../main/webapp/app/entities/parametres/parametres.model';

describe('Component Tests', () => {

    describe('Parametres Management Component', () => {
        let comp: ParametresComponent;
        let fixture: ComponentFixture<ParametresComponent>;
        let service: ParametresService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EbankingTestModule],
                declarations: [ParametresComponent],
                providers: [
                    ParametresService
                ]
            })
            .overrideTemplate(ParametresComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ParametresComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ParametresService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Parametres(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.parametres[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
