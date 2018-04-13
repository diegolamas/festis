/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FestisTestModule } from '../../../test.module';
import { EditionComponent } from '../../../../../../main/webapp/app/entities/edition/edition.component';
import { EditionService } from '../../../../../../main/webapp/app/entities/edition/edition.service';
import { Edition } from '../../../../../../main/webapp/app/entities/edition/edition.model';

describe('Component Tests', () => {

    describe('Edition Management Component', () => {
        let comp: EditionComponent;
        let fixture: ComponentFixture<EditionComponent>;
        let service: EditionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FestisTestModule],
                declarations: [EditionComponent],
                providers: [
                    EditionService
                ]
            })
            .overrideTemplate(EditionComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EditionComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EditionService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Edition(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.editions[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
