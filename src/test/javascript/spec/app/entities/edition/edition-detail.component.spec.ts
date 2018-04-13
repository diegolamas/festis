/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { FestisTestModule } from '../../../test.module';
import { EditionDetailComponent } from '../../../../../../main/webapp/app/entities/edition/edition-detail.component';
import { EditionService } from '../../../../../../main/webapp/app/entities/edition/edition.service';
import { Edition } from '../../../../../../main/webapp/app/entities/edition/edition.model';

describe('Component Tests', () => {

    describe('Edition Management Detail Component', () => {
        let comp: EditionDetailComponent;
        let fixture: ComponentFixture<EditionDetailComponent>;
        let service: EditionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FestisTestModule],
                declarations: [EditionDetailComponent],
                providers: [
                    EditionService
                ]
            })
            .overrideTemplate(EditionDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EditionDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EditionService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Edition(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.edition).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
