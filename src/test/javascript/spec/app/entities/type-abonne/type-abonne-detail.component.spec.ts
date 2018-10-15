/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { EbankingTestModule } from '../../../test.module';
import { TypeAbonneDetailComponent } from '../../../../../../main/webapp/app/entities/type-abonne/type-abonne-detail.component';
import { TypeAbonneService } from '../../../../../../main/webapp/app/entities/type-abonne/type-abonne.service';
import { TypeAbonne } from '../../../../../../main/webapp/app/entities/type-abonne/type-abonne.model';

describe('Component Tests', () => {

    describe('TypeAbonne Management Detail Component', () => {
        let comp: TypeAbonneDetailComponent;
        let fixture: ComponentFixture<TypeAbonneDetailComponent>;
        let service: TypeAbonneService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EbankingTestModule],
                declarations: [TypeAbonneDetailComponent],
                providers: [
                    TypeAbonneService
                ]
            })
            .overrideTemplate(TypeAbonneDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TypeAbonneDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TypeAbonneService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new TypeAbonne(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.typeAbonne).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
