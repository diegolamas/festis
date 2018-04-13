import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Festival } from './festival.model';
import { FestivalService } from './festival.service';

@Component({
    selector: 'jhi-festival-detail',
    templateUrl: './festival-detail.component.html'
})
export class FestivalDetailComponent implements OnInit, OnDestroy {

    festival: Festival;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private festivalService: FestivalService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInFestivals();
    }

    load(id) {
        this.festivalService.find(id)
            .subscribe((festivalResponse: HttpResponse<Festival>) => {
                this.festival = festivalResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInFestivals() {
        this.eventSubscriber = this.eventManager.subscribe(
            'festivalListModification',
            (response) => this.load(this.festival.id)
        );
    }
}
