/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { EbankingTestModule } from '../../../test.module';
import { TypeCompteDetailComponent } from '../../../../../../main/webapp/app/entities/type-compte/type-compte-detail.component';
import { TypeCompteService } from '../../../../../../main/webapp/app/entities/type-compte/type-compte.service';
import { TypeCompte } from '../../../../../../main/webapp/app/entities/type-compte/type-compte.model';

describe('Component Tests', () => {

    describe('TypeCompte Management Detail Component', () => {
        let comp: TypeCompteDetailComponent;
        let fixture: ComponentFixture<TypeCompteDetailComponent>;
        let service: TypeCompteService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EbankingTestModule],
                declarations: [TypeCompteDetailComponent],
                providers: [
                    TypeCompteService
                ]
            })
            .overrideTemplate(TypeCompteDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TypeCompteDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TypeCompteService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new TypeCompte(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.typeCompte).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
