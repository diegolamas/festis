import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { Edition } from './edition.model';
import { EditionService } from './edition.service';

@Component({
    selector: 'jhi-edition-detail',
    templateUrl: './edition-detail.component.html'
})
export class EditionDetailComponent implements OnInit, OnDestroy {

    edition: Edition;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private editionService: EditionService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInEditions();
    }

    load(id) {
        this.editionService.find(id)
            .subscribe((editionResponse: HttpResponse<Edition>) => {
                this.edition = editionResponse.body;
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

    registerChangeInEditions() {
        this.eventSubscriber = this.eventManager.subscribe(
            'editionListModification',
            (response) => this.load(this.edition.id)
        );
    }
}
