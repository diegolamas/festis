import { Component, OnInit, OnDestroy, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { Announcement } from './announcement.model';
import { AnnouncementPopupService } from './announcement-popup.service';
import { AnnouncementService } from './announcement.service';
import { Artist, ArtistService } from '../artist';
import { Edition, EditionService } from '../edition';

@Component({
    selector: 'jhi-announcement-dialog',
    templateUrl: './announcement-dialog.component.html'
})
export class AnnouncementDialogComponent implements OnInit {

    announcement: Announcement;
    isSaving: boolean;

    artists: Artist[];

    editions: Edition[];

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private announcementService: AnnouncementService,
        private artistService: ArtistService,
        private editionService: EditionService,
        private elementRef: ElementRef,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.artistService.query()
            .subscribe((res: HttpResponse<Artist[]>) => { this.artists = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.editionService.query()
            .subscribe((res: HttpResponse<Edition[]>) => { this.editions = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.announcement, this.elementRef, field, fieldContentType, idInput);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.announcement.id !== undefined) {
            this.subscribeToSaveResponse(
                this.announcementService.update(this.announcement));
        } else {
            this.subscribeToSaveResponse(
                this.announcementService.create(this.announcement));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Announcement>>) {
        result.subscribe((res: HttpResponse<Announcement>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Announcement) {
        this.eventManager.broadcast({ name: 'announcementListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackArtistById(index: number, item: Artist) {
        return item.id;
    }

    trackEditionById(index: number, item: Edition) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-announcement-popup',
    template: ''
})
export class AnnouncementPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private announcementPopupService: AnnouncementPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.announcementPopupService
                    .open(AnnouncementDialogComponent as Component, params['id']);
            } else {
                this.announcementPopupService
                    .open(AnnouncementDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
