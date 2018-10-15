/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { EbankingTestModule } from '../../../test.module';
import { TypeCarteDetailComponent } from '../../../../../../main/webapp/app/entities/type-carte/type-carte-detail.component';
import { TypeCarteService } from '../../../../../../main/webapp/app/entities/type-carte/type-carte.service';
import { TypeCarte } from '../../../../../../main/webapp/app/entities/type-carte/type-carte.model';

describe('Component Tests', () => {

    describe('TypeCarte Management Detail Component', () => {
        let comp: TypeCarteDetailComponent;
        let fixture: ComponentFixture<TypeCarteDetailComponent>;
        let service: TypeCarteService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EbankingTestModule],
                declarations: [TypeCarteDetailComponent],
                providers: [
                    TypeCarteService
                ]
            })
            .overrideTemplate(TypeCarteDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TypeCarteDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TypeCarteService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new TypeCarte(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.typeCarte).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
