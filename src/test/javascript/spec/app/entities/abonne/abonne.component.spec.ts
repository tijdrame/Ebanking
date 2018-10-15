/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EbankingTestModule } from '../../../test.module';
import { AbonneComponent } from '../../../../../../main/webapp/app/entities/abonne/abonne.component';
import { AbonneService } from '../../../../../../main/webapp/app/entities/abonne/abonne.service';
import { Abonne } from '../../../../../../main/webapp/app/entities/abonne/abonne.model';

describe('Component Tests', () => {

    describe('Abonne Management Component', () => {
        let comp: AbonneComponent;
        let fixture: ComponentFixture<AbonneComponent>;
        let service: AbonneService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EbankingTestModule],
                declarations: [AbonneComponent],
                providers: [
                    AbonneService
                ]
            })
            .overrideTemplate(AbonneComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AbonneComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AbonneService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Abonne(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.abonnes[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
