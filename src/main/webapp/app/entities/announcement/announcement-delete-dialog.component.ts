import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Announcement } from './announcement.model';
import { AnnouncementPopupService } from './announcement-popup.service';
import { AnnouncementService } from './announcement.service';

@Component({
    selector: 'jhi-announcement-delete-dialog',
    templateUrl: './announcement-delete-dialog.component.html'
})
export class AnnouncementDeleteDialogComponent {

    announcement: Announcement;

    constructor(
        private announcementService: AnnouncementService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.announcementService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'announcementListModification',
                content: 'Deleted an announcement'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-announcement-delete-popup',
    template: ''
})
export class AnnouncementDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private announcementPopupService: AnnouncementPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.announcementPopupService
                .open(AnnouncementDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
