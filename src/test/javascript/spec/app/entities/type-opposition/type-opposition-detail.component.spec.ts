/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { EbankingTestModule } from '../../../test.module';
import { TypeOppositionDetailComponent } from '../../../../../../main/webapp/app/entities/type-opposition/type-opposition-detail.component';
import { TypeOppositionService } from '../../../../../../main/webapp/app/entities/type-opposition/type-opposition.service';
import { TypeOpposition } from '../../../../../../main/webapp/app/entities/type-opposition/type-opposition.model';

describe('Component Tests', () => {

    describe('TypeOpposition Management Detail Component', () => {
        let comp: TypeOppositionDetailComponent;
        let fixture: ComponentFixture<TypeOppositionDetailComponent>;
        let service: TypeOppositionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EbankingTestModule],
                declarations: [TypeOppositionDetailComponent],
                providers: [
                    TypeOppositionService
                ]
            })
            .overrideTemplate(TypeOppositionDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TypeOppositionDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TypeOppositionService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new TypeOpposition(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.typeOpposition).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
