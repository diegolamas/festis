/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { FestisTestModule } from '../../../test.module';
import { ArtistDetailComponent } from '../../../../../../main/webapp/app/entities/artist/artist-detail.component';
import { ArtistService } from '../../../../../../main/webapp/app/entities/artist/artist.service';
import { Artist } from '../../../../../../main/webapp/app/entities/artist/artist.model';

describe('Component Tests', () => {

    describe('Artist Management Detail Component', () => {
        let comp: ArtistDetailComponent;
        let fixture: ComponentFixture<ArtistDetailComponent>;
        let service: ArtistService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FestisTestModule],
                declarations: [ArtistDetailComponent],
                providers: [
                    ArtistService
                ]
            })
            .overrideTemplate(ArtistDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ArtistDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ArtistService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Artist(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.artist).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
