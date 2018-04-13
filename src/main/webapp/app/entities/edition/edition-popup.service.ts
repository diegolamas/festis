import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { Edition } from './edition.model';
import { EditionService } from './edition.service';

@Injectable()
export class EditionPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private editionService: EditionService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.editionService.find(id)
                    .subscribe((editionResponse: HttpResponse<Edition>) => {
                        const edition: Edition = editionResponse.body;
                        if (edition.startDate) {
                            edition.startDate = {
                                year: edition.startDate.getFullYear(),
                                month: edition.startDate.getMonth() + 1,
                                day: edition.startDate.getDate()
                            };
                        }
                        if (edition.endDate) {
                            edition.endDate = {
                                year: edition.endDate.getFullYear(),
                                month: edition.endDate.getMonth() + 1,
                                day: edition.endDate.getDate()
                            };
                        }
                        this.ngbModalRef = this.editionModalRef(component, edition);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.editionModalRef(component, new Edition());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    editionModalRef(component: Component, edition: Edition): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.edition = edition;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
