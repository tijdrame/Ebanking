/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EbankingTestModule } from '../../../test.module';
import { TypeChequierComponent } from '../../../../../../main/webapp/app/entities/type-chequier/type-chequier.component';
import { TypeChequierService } from '../../../../../../main/webapp/app/entities/type-chequier/type-chequier.service';
import { TypeChequier } from '../../../../../../main/webapp/app/entities/type-chequier/type-chequier.model';

describe('Component Tests', () => {

    describe('TypeChequier Management Component', () => {
        let comp: TypeChequierComponent;
        let fixture: ComponentFixture<TypeChequierComponent>;
        let service: TypeChequierService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EbankingTestModule],
                declarations: [TypeChequierComponent],
                providers: [
                    TypeChequierService
                ]
            })
            .overrideTemplate(TypeChequierComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TypeChequierComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TypeChequierService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new TypeChequier(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.typeChequiers[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
