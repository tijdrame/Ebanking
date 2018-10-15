/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { EbankingTestModule } from '../../../test.module';
import { OperationTypeDetailComponent } from '../../../../../../main/webapp/app/entities/operation-type/operation-type-detail.component';
import { OperationTypeService } from '../../../../../../main/webapp/app/entities/operation-type/operation-type.service';
import { OperationType } from '../../../../../../main/webapp/app/entities/operation-type/operation-type.model';

describe('Component Tests', () => {

    describe('OperationType Management Detail Component', () => {
        let comp: OperationTypeDetailComponent;
        let fixture: ComponentFixture<OperationTypeDetailComponent>;
        let service: OperationTypeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EbankingTestModule],
                declarations: [OperationTypeDetailComponent],
                providers: [
                    OperationTypeService
                ]
            })
            .overrideTemplate(OperationTypeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OperationTypeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OperationTypeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new OperationType(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.operationType).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
