/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EbankingTestModule } from '../../../test.module';
import { NbreFeuillesChequierComponent } from '../../../../../../main/webapp/app/entities/nbre-feuilles-chequier/nbre-feuilles-chequier.component';
import { NbreFeuillesChequierService } from '../../../../../../main/webapp/app/entities/nbre-feuilles-chequier/nbre-feuilles-chequier.service';
import { NbreFeuillesChequier } from '../../../../../../main/webapp/app/entities/nbre-feuilles-chequier/nbre-feuilles-chequier.model';

describe('Component Tests', () => {

    describe('NbreFeuillesChequier Management Component', () => {
        let comp: NbreFeuillesChequierComponent;
        let fixture: ComponentFixture<NbreFeuillesChequierComponent>;
        let service: NbreFeuillesChequierService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EbankingTestModule],
                declarations: [NbreFeuillesChequierComponent],
                providers: [
                    NbreFeuillesChequierService
                ]
            })
            .overrideTemplate(NbreFeuillesChequierComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(NbreFeuillesChequierComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NbreFeuillesChequierService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new NbreFeuillesChequier(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.nbreFeuillesChequiers[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
