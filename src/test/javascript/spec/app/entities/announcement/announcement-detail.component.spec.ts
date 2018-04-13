/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { FestisTestModule } from '../../../test.module';
import { AnnouncementDetailComponent } from '../../../../../../main/webapp/app/entities/announcement/announcement-detail.component';
import { AnnouncementService } from '../../../../../../main/webapp/app/entities/announcement/announcement.service';
import { Announcement } from '../../../../../../main/webapp/app/entities/announcement/announcement.model';

describe('Component Tests', () => {

    describe('Announcement Management Detail Component', () => {
        let comp: AnnouncementDetailComponent;
        let fixture: ComponentFixture<AnnouncementDetailComponent>;
        let service: AnnouncementService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FestisTestModule],
                declarations: [AnnouncementDetailComponent],
                providers: [
                    AnnouncementService
                ]
            })
            .overrideTemplate(AnnouncementDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AnnouncementDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AnnouncementService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Announcement(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.announcement).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
