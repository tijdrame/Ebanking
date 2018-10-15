/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { EbankingTestModule } from '../../../test.module';
import { TypeChequierDetailComponent } from '../../../../../../main/webapp/app/entities/type-chequier/type-chequier-detail.component';
import { TypeChequierService } from '../../../../../../main/webapp/app/entities/type-chequier/type-chequier.service';
import { TypeChequier } from '../../../../../../main/webapp/app/entities/type-chequier/type-chequier.model';

describe('Component Tests', () => {

    describe('TypeChequier Management Detail Component', () => {
        let comp: TypeChequierDetailComponent;
        let fixture: ComponentFixture<TypeChequierDetailComponent>;
        let service: TypeChequierService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EbankingTestModule],
                declarations: [TypeChequierDetailComponent],
                providers: [
                    TypeChequierService
                ]
            })
            .overrideTemplate(TypeChequierDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TypeChequierDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TypeChequierService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new TypeChequier(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.typeChequier).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
