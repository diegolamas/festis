/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FestisTestModule } from '../../../test.module';
import { ArtistComponent } from '../../../../../../main/webapp/app/entities/artist/artist.component';
import { ArtistService } from '../../../../../../main/webapp/app/entities/artist/artist.service';
import { Artist } from '../../../../../../main/webapp/app/entities/artist/artist.model';

describe('Component Tests', () => {

    describe('Artist Management Component', () => {
        let comp: ArtistComponent;
        let fixture: ComponentFixture<ArtistComponent>;
        let service: ArtistService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FestisTestModule],
                declarations: [ArtistComponent],
                providers: [
                    ArtistService
                ]
            })
            .overrideTemplate(ArtistComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ArtistComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ArtistService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Artist(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.artists[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
