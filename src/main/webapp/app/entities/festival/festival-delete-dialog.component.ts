import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Festival } from './festival.model';
import { FestivalPopupService } from './festival-popup.service';
import { FestivalService } from './festival.service';

@Component({
    selector: 'jhi-festival-delete-dialog',
    templateUrl: './festival-delete-dialog.component.html'
})
export class FestivalDeleteDialogComponent {

    festival: Festival;

    constructor(
        private festivalService: FestivalService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.festivalService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'festivalListModification',
                content: 'Deleted an festival'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-festival-delete-popup',
    template: ''
})
export class FestivalDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private festivalPopupService: FestivalPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.festivalPopupService
                .open(FestivalDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
