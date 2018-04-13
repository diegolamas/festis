import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Attend } from './attend.model';
import { AttendService } from './attend.service';

@Component({
    selector: 'jhi-attend-detail',
    templateUrl: './attend-detail.component.html'
})
export class AttendDetailComponent implements OnInit, OnDestroy {

    attend: Attend;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private attendService: AttendService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAttends();
    }

    load(id) {
        this.attendService.find(id)
            .subscribe((attendResponse: HttpResponse<Attend>) => {
                this.attend = attendResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAttends() {
        this.eventSubscriber = this.eventManager.subscribe(
            'attendListModification',
            (response) => this.load(this.attend.id)
        );
    }
}
