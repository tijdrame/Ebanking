/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EbankingTestModule } from '../../../test.module';
import { OperationsVirementComponent } from '../../../../../../main/webapp/app/entities/operations-virement/operations-virement.component';
import { OperationsVirementService } from '../../../../../../main/webapp/app/entities/operations-virement/operations-virement.service';
import { OperationsVirement } from '../../../../../../main/webapp/app/entities/operations-virement/operations-virement.model';

describe('Component Tests', () => {

    describe('OperationsVirement Management Component', () => {
        let comp: OperationsVirementComponent;
        let fixture: ComponentFixture<OperationsVirementComponent>;
        let service: OperationsVirementService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EbankingTestModule],
                declarations: [OperationsVirementComponent],
                providers: [
                    OperationsVirementService
                ]
            })
            .overrideTemplate(OperationsVirementComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OperationsVirementComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OperationsVirementService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new OperationsVirement(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.operationsVirements[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
