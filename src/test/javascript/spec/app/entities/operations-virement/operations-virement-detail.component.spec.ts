/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { EbankingTestModule } from '../../../test.module';
import { OperationsVirementDetailComponent } from '../../../../../../main/webapp/app/entities/operations-virement/operations-virement-detail.component';
import { OperationsVirementService } from '../../../../../../main/webapp/app/entities/operations-virement/operations-virement.service';
import { OperationsVirement } from '../../../../../../main/webapp/app/entities/operations-virement/operations-virement.model';

describe('Component Tests', () => {

    describe('OperationsVirement Management Detail Component', () => {
        let comp: OperationsVirementDetailComponent;
        let fixture: ComponentFixture<OperationsVirementDetailComponent>;
        let service: OperationsVirementService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EbankingTestModule],
                declarations: [OperationsVirementDetailComponent],
                providers: [
                    OperationsVirementService
                ]
            })
            .overrideTemplate(OperationsVirementDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OperationsVirementDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OperationsVirementService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new OperationsVirement(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.operationsVirement).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
