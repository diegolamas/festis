import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Attend } from './attend.model';
import { AttendPopupService } from './attend-popup.service';
import { AttendService } from './attend.service';
import { User, UserService } from '../../shared';
import { Edition, EditionService } from '../edition';

@Component({
    selector: 'jhi-attend-dialog',
    templateUrl: './attend-dialog.component.html'
})
export class AttendDialogComponent implements OnInit {

    attend: Attend;
    isSaving: boolean;

    users: User[];

    editions: Edition[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private attendService: AttendService,
        private userService: UserService,
        private editionService: EditionService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.userService.query()
            .subscribe((res: HttpResponse<User[]>) => { this.users = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.editionService.query()
            .subscribe((res: HttpResponse<Edition[]>) => { this.editions = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.attend.id !== undefined) {
            this.subscribeToSaveResponse(
                this.attendService.update(this.attend));
        } else {
            this.subscribeToSaveResponse(
                this.attendService.create(this.attend));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Attend>>) {
        result.subscribe((res: HttpResponse<Attend>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Attend) {
        this.eventManager.broadcast({ name: 'attendListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }

    trackEditionById(index: number, item: Edition) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-attend-popup',
    template: ''
})
export class AttendPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private attendPopupService: AttendPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.attendPopupService
                    .open(AttendDialogComponent as Component, params['id']);
            } else {
                this.attendPopupService
                    .open(AttendDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
