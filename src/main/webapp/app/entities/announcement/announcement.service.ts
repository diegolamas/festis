import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Announcement } from './announcement.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Announcement>;

@Injectable()
export class AnnouncementService {

    private resourceUrl =  SERVER_API_URL + 'api/announcements';

    constructor(private http: HttpClient) { }

    create(announcement: Announcement): Observable<EntityResponseType> {
        const copy = this.convert(announcement);
        return this.http.post<Announcement>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(announcement: Announcement): Observable<EntityResponseType> {
        const copy = this.convert(announcement);
        return this.http.put<Announcement>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Announcement>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Announcement[]>> {
        const options = createRequestOption(req);
        return this.http.get<Announcement[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Announcement[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Announcement = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Announcement[]>): HttpResponse<Announcement[]> {
        const jsonResponse: Announcement[] = res.body;
        const body: Announcement[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Announcement.
     */
    private convertItemFromServer(announcement: Announcement): Announcement {
        const copy: Announcement = Object.assign({}, announcement);
        return copy;
    }

    /**
     * Convert a Announcement to a JSON which can be sent to the server.
     */
    private convert(announcement: Announcement): Announcement {
        const copy: Announcement = Object.assign({}, announcement);
        return copy;
    }
}
