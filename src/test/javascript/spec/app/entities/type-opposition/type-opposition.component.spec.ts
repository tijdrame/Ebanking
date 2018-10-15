/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EbankingTestModule } from '../../../test.module';
import { TypeOppositionComponent } from '../../../../../../main/webapp/app/entities/type-opposition/type-opposition.component';
import { TypeOppositionService } from '../../../../../../main/webapp/app/entities/type-opposition/type-opposition.service';
import { TypeOpposition } from '../../../../../../main/webapp/app/entities/type-opposition/type-opposition.model';

describe('Component Tests', () => {

    describe('TypeOpposition Management Component', () => {
        let comp: TypeOppositionComponent;
        let fixture: ComponentFixture<TypeOppositionComponent>;
        let service: TypeOppositionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EbankingTestModule],
                declarations: [TypeOppositionComponent],
                providers: [
                    TypeOppositionService
                ]
            })
            .overrideTemplate(TypeOppositionComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TypeOppositionComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TypeOppositionService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new TypeOpposition(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.typeOppositions[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
