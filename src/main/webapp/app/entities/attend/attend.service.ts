import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Attend } from './attend.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Attend>;

@Injectable()
export class AttendService {

    private resourceUrl =  SERVER_API_URL + 'api/attends';

    constructor(private http: HttpClient) { }

    create(attend: Attend): Observable<EntityResponseType> {
        const copy = this.convert(attend);
        return this.http.post<Attend>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(attend: Attend): Observable<EntityResponseType> {
        const copy = this.convert(attend);
        return this.http.put<Attend>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Attend>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Attend[]>> {
        const options = createRequestOption(req);
        return this.http.get<Attend[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Attend[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Attend = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Attend[]>): HttpResponse<Attend[]> {
        const jsonResponse: Attend[] = res.body;
        const body: Attend[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Attend.
     */
    private convertItemFromServer(attend: Attend): Attend {
        const copy: Attend = Object.assign({}, attend);
        return copy;
    }

    /**
     * Convert a Attend to a JSON which can be sent to the server.
     */
    private convert(attend: Attend): Attend {
        const copy: Attend = Object.assign({}, attend);
        return copy;
    }
}
