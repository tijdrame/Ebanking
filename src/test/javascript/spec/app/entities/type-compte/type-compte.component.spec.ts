/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EbankingTestModule } from '../../../test.module';
import { TypeCompteComponent } from '../../../../../../main/webapp/app/entities/type-compte/type-compte.component';
import { TypeCompteService } from '../../../../../../main/webapp/app/entities/type-compte/type-compte.service';
import { TypeCompte } from '../../../../../../main/webapp/app/entities/type-compte/type-compte.model';

describe('Component Tests', () => {

    describe('TypeCompte Management Component', () => {
        let comp: TypeCompteComponent;
        let fixture: ComponentFixture<TypeCompteComponent>;
        let service: TypeCompteService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EbankingTestModule],
                declarations: [TypeCompteComponent],
                providers: [
                    TypeCompteService
                ]
            })
            .overrideTemplate(TypeCompteComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TypeCompteComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TypeCompteService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new TypeCompte(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.typeComptes[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
