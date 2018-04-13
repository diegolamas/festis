import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Follow } from './follow.model';
import { FollowPopupService } from './follow-popup.service';
import { FollowService } from './follow.service';
import { User, UserService } from '../../shared';
import { Artist, ArtistService } from '../artist';

@Component({
    selector: 'jhi-follow-dialog',
    templateUrl: './follow-dialog.component.html'
})
export class FollowDialogComponent implements OnInit {

    follow: Follow;
    isSaving: boolean;

    users: User[];

    artists: Artist[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private followService: FollowService,
        private userService: UserService,
        private artistService: ArtistService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.userService.query()
            .subscribe((res: HttpResponse<User[]>) => { this.users = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.artistService.query()
            .subscribe((res: HttpResponse<Artist[]>) => { this.artists = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.follow.id !== undefined) {
            this.subscribeToSaveResponse(
                this.followService.update(this.follow));
        } else {
            this.subscribeToSaveResponse(
                this.followService.create(this.follow));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Follow>>) {
        result.subscribe((res: HttpResponse<Follow>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Follow) {
        this.eventManager.broadcast({ name: 'followListModification', content: 'OK'});
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

    trackArtistById(index: number, item: Artist) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-follow-popup',
    template: ''
})
export class FollowPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private followPopupService: FollowPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.followPopupService
                    .open(FollowDialogComponent as Component, params['id']);
            } else {
                this.followPopupService
                    .open(FollowDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
