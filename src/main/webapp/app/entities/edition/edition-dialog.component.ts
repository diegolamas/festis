import { Component, OnInit, OnDestroy, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { Edition } from './edition.model';
import { EditionPopupService } from './edition-popup.service';
import { EditionService } from './edition.service';
import { Festival, FestivalService } from '../festival';

@Component({
    selector: 'jhi-edition-dialog',
    templateUrl: './edition-dialog.component.html'
})
export class EditionDialogComponent implements OnInit {

    edition: Edition;
    isSaving: boolean;

    festivals: Festival[];
    startDateDp: any;
    endDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private editionService: EditionService,
        private festivalService: FestivalService,
        private elementRef: ElementRef,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.festivalService.query()
            .subscribe((res: HttpResponse<Festival[]>) => { this.festivals = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
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
        this.dataUtils.clearInputImage(this.edition, this.elementRef, field, fieldContentType, idInput);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.edition.id !== undefined) {
            this.subscribeToSaveResponse(
                this.editionService.update(this.edition));
        } else {
            this.subscribeToSaveResponse(
                this.editionService.create(this.edition));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Edition>>) {
        result.subscribe((res: HttpResponse<Edition>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Edition) {
        this.eventManager.broadcast({ name: 'editionListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackFestivalById(index: number, item: Festival) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-edition-popup',
    template: ''
})
export class EditionPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private editionPopupService: EditionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.editionPopupService
                    .open(EditionDialogComponent as Component, params['id']);
            } else {
                this.editionPopupService
                    .open(EditionDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
