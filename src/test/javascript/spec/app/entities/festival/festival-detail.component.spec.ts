/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { FestisTestModule } from '../../../test.module';
import { FestivalDetailComponent } from '../../../../../../main/webapp/app/entities/festival/festival-detail.component';
import { FestivalService } from '../../../../../../main/webapp/app/entities/festival/festival.service';
import { Festival } from '../../../../../../main/webapp/app/entities/festival/festival.model';

describe('Component Tests', () => {

    describe('Festival Management Detail Component', () => {
        let comp: FestivalDetailComponent;
        let fixture: ComponentFixture<FestivalDetailComponent>;
        let service: FestivalService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FestisTestModule],
                declarations: [FestivalDetailComponent],
                providers: [
                    FestivalService
                ]
            })
            .overrideTemplate(FestivalDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FestivalDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FestivalService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Festival(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.festival).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
