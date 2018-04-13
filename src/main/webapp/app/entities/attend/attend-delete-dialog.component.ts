import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Attend } from './attend.model';
import { AttendPopupService } from './attend-popup.service';
import { AttendService } from './attend.service';

@Component({
    selector: 'jhi-attend-delete-dialog',
    templateUrl: './attend-delete-dialog.component.html'
})
export class AttendDeleteDialogComponent {

    attend: Attend;

    constructor(
        private attendService: AttendService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.attendService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'attendListModification',
                content: 'Deleted an attend'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-attend-delete-popup',
    template: ''
})
export class AttendDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private attendPopupService: AttendPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.attendPopupService
                .open(AttendDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
