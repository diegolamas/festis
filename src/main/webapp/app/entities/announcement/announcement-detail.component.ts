import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { Announcement } from './announcement.model';
import { AnnouncementService } from './announcement.service';

@Component({
    selector: 'jhi-announcement-detail',
    templateUrl: './announcement-detail.component.html'
})
export class AnnouncementDetailComponent implements OnInit, OnDestroy {

    announcement: Announcement;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private announcementService: AnnouncementService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAnnouncements();
    }

    load(id) {
        this.announcementService.find(id)
            .subscribe((announcementResponse: HttpResponse<Announcement>) => {
                this.announcement = announcementResponse.body;
            });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAnnouncements() {
        this.eventSubscriber = this.eventManager.subscribe(
            'announcementListModification',
            (response) => this.load(this.announcement.id)
        );
    }
}
