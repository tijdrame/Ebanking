/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { EbankingTestModule } from '../../../test.module';
import { NbreFeuillesChequierDetailComponent } from '../../../../../../main/webapp/app/entities/nbre-feuilles-chequier/nbre-feuilles-chequier-detail.component';
import { NbreFeuillesChequierService } from '../../../../../../main/webapp/app/entities/nbre-feuilles-chequier/nbre-feuilles-chequier.service';
import { NbreFeuillesChequier } from '../../../../../../main/webapp/app/entities/nbre-feuilles-chequier/nbre-feuilles-chequier.model';

describe('Component Tests', () => {

    describe('NbreFeuillesChequier Management Detail Component', () => {
        let comp: NbreFeuillesChequierDetailComponent;
        let fixture: ComponentFixture<NbreFeuillesChequierDetailComponent>;
        let service: NbreFeuillesChequierService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EbankingTestModule],
                declarations: [NbreFeuillesChequierDetailComponent],
                providers: [
                    NbreFeuillesChequierService
                ]
            })
            .overrideTemplate(NbreFeuillesChequierDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(NbreFeuillesChequierDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NbreFeuillesChequierService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new NbreFeuillesChequier(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.nbreFeuillesChequier).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
