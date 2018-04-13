/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FestisTestModule } from '../../../test.module';
import { FestivalComponent } from '../../../../../../main/webapp/app/entities/festival/festival.component';
import { FestivalService } from '../../../../../../main/webapp/app/entities/festival/festival.service';
import { Festival } from '../../../../../../main/webapp/app/entities/festival/festival.model';

describe('Component Tests', () => {

    describe('Festival Management Component', () => {
        let comp: FestivalComponent;
        let fixture: ComponentFixture<FestivalComponent>;
        let service: FestivalService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FestisTestModule],
                declarations: [FestivalComponent],
                providers: [
                    FestivalService
                ]
            })
            .overrideTemplate(FestivalComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FestivalComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FestivalService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Festival(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.festivals[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
