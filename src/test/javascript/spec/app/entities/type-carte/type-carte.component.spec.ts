/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EbankingTestModule } from '../../../test.module';
import { TypeCarteComponent } from '../../../../../../main/webapp/app/entities/type-carte/type-carte.component';
import { TypeCarteService } from '../../../../../../main/webapp/app/entities/type-carte/type-carte.service';
import { TypeCarte } from '../../../../../../main/webapp/app/entities/type-carte/type-carte.model';

describe('Component Tests', () => {

    describe('TypeCarte Management Component', () => {
        let comp: TypeCarteComponent;
        let fixture: ComponentFixture<TypeCarteComponent>;
        let service: TypeCarteService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EbankingTestModule],
                declarations: [TypeCarteComponent],
                providers: [
                    TypeCarteService
                ]
            })
            .overrideTemplate(TypeCarteComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TypeCarteComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TypeCarteService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new TypeCarte(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.typeCartes[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
