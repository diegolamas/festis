/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { FestisTestModule } from '../../../test.module';
import { AttendDetailComponent } from '../../../../../../main/webapp/app/entities/attend/attend-detail.component';
import { AttendService } from '../../../../../../main/webapp/app/entities/attend/attend.service';
import { Attend } from '../../../../../../main/webapp/app/entities/attend/attend.model';

describe('Component Tests', () => {

    describe('Attend Management Detail Component', () => {
        let comp: AttendDetailComponent;
        let fixture: ComponentFixture<AttendDetailComponent>;
        let service: AttendService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FestisTestModule],
                declarations: [AttendDetailComponent],
                providers: [
                    AttendService
                ]
            })
            .overrideTemplate(AttendDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AttendDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AttendService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Attend(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.attend).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
