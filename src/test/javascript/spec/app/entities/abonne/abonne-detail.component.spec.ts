/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { EbankingTestModule } from '../../../test.module';
import { AbonneDetailComponent } from '../../../../../../main/webapp/app/entities/abonne/abonne-detail.component';
import { AbonneService } from '../../../../../../main/webapp/app/entities/abonne/abonne.service';
import { Abonne } from '../../../../../../main/webapp/app/entities/abonne/abonne.model';

describe('Component Tests', () => {

    describe('Abonne Management Detail Component', () => {
        let comp: AbonneDetailComponent;
        let fixture: ComponentFixture<AbonneDetailComponent>;
        let service: AbonneService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EbankingTestModule],
                declarations: [AbonneDetailComponent],
                providers: [
                    AbonneService
                ]
            })
            .overrideTemplate(AbonneDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AbonneDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AbonneService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Abonne(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.abonne).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
