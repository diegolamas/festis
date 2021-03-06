import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Artist } from './artist.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Artist>;

@Injectable()
export class ArtistService {

    private resourceUrl =  SERVER_API_URL + 'api/artists';

    constructor(private http: HttpClient) { }

    create(artist: Artist): Observable<EntityResponseType> {
        const copy = this.convert(artist);
        return this.http.post<Artist>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(artist: Artist): Observable<EntityResponseType> {
        const copy = this.convert(artist);
        return this.http.put<Artist>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Artist>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Artist[]>> {
        const options = createRequestOption(req);
        return this.http.get<Artist[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Artist[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Artist = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Artist[]>): HttpResponse<Artist[]> {
        const jsonResponse: Artist[] = res.body;
        const body: Artist[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Artist.
     */
    private convertItemFromServer(artist: Artist): Artist {
        const copy: Artist = Object.assign({}, artist);
        return copy;
    }

    /**
     * Convert a Artist to a JSON which can be sent to the server.
     */
    private convert(artist: Artist): Artist {
        const copy: Artist = Object.assign({}, artist);
        return copy;
    }
}
