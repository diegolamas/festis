/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FestisTestModule } from '../../../test.module';
import { AttendComponent } from '../../../../../../main/webapp/app/entities/attend/attend.component';
import { AttendService } from '../../../../../../main/webapp/app/entities/attend/attend.service';
import { Attend } from '../../../../../../main/webapp/app/entities/attend/attend.model';

describe('Component Tests', () => {

    describe('Attend Management Component', () => {
        let comp: AttendComponent;
        let fixture: ComponentFixture<AttendComponent>;
        let service: AttendService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FestisTestModule],
                declarations: [AttendComponent],
                providers: [
                    AttendService
                ]
            })
            .overrideTemplate(AttendComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AttendComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AttendService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Attend(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.attends[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
