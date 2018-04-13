import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Festival } from './festival.model';
import { FestivalPopupService } from './festival-popup.service';
import { FestivalService } from './festival.service';

@Component({
    selector: 'jhi-festival-dialog',
    templateUrl: './festival-dialog.component.html'
})
export class FestivalDialogComponent implements OnInit {

    festival: Festival;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private festivalService: FestivalService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.festival.id !== undefined) {
            this.subscribeToSaveResponse(
                this.festivalService.update(this.festival));
        } else {
            this.subscribeToSaveResponse(
                this.festivalService.create(this.festival));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Festival>>) {
        result.subscribe((res: HttpResponse<Festival>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Festival) {
        this.eventManager.broadcast({ name: 'festivalListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-festival-popup',
    template: ''
})
export class FestivalPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private festivalPopupService: FestivalPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.festivalPopupService
                    .open(FestivalDialogComponent as Component, params['id']);
            } else {
                this.festivalPopupService
                    .open(FestivalDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
