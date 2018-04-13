/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { FestisTestModule } from '../../../test.module';
import { AnnouncementDialogComponent } from '../../../../../../main/webapp/app/entities/announcement/announcement-dialog.component';
import { AnnouncementService } from '../../../../../../main/webapp/app/entities/announcement/announcement.service';
import { Announcement } from '../../../../../../main/webapp/app/entities/announcement/announcement.model';
import { ArtistService } from '../../../../../../main/webapp/app/entities/artist';
import { EditionService } from '../../../../../../main/webapp/app/entities/edition';

describe('Component Tests', () => {

    describe('Announcement Management Dialog Component', () => {
        let comp: AnnouncementDialogComponent;
        let fixture: ComponentFixture<AnnouncementDialogComponent>;
        let service: AnnouncementService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FestisTestModule],
                declarations: [AnnouncementDialogComponent],
                providers: [
                    ArtistService,
                    EditionService,
                    AnnouncementService
                ]
            })
            .overrideTemplate(AnnouncementDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AnnouncementDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AnnouncementService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Announcement(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.announcement = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'announcementListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Announcement();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.announcement = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'announcementListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
