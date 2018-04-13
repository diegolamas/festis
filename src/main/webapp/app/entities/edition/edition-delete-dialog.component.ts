import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Edition } from './edition.model';
import { EditionPopupService } from './edition-popup.service';
import { EditionService } from './edition.service';

@Component({
    selector: 'jhi-edition-delete-dialog',
    templateUrl: './edition-delete-dialog.component.html'
})
export class EditionDeleteDialogComponent {

    edition: Edition;

    constructor(
        private editionService: EditionService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.editionService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'editionListModification',
                content: 'Deleted an edition'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-edition-delete-popup',
    template: ''
})
export class EditionDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private editionPopupService: EditionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.editionPopupService
                .open(EditionDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
