/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EbankingTestModule } from '../../../test.module';
import { TypeAbonneComponent } from '../../../../../../main/webapp/app/entities/type-abonne/type-abonne.component';
import { TypeAbonneService } from '../../../../../../main/webapp/app/entities/type-abonne/type-abonne.service';
import { TypeAbonne } from '../../../../../../main/webapp/app/entities/type-abonne/type-abonne.model';

describe('Component Tests', () => {

    describe('TypeAbonne Management Component', () => {
        let comp: TypeAbonneComponent;
        let fixture: ComponentFixture<TypeAbonneComponent>;
        let service: TypeAbonneService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EbankingTestModule],
                declarations: [TypeAbonneComponent],
                providers: [
                    TypeAbonneService
                ]
            })
            .overrideTemplate(TypeAbonneComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TypeAbonneComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TypeAbonneService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new TypeAbonne(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.typeAbonnes[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
