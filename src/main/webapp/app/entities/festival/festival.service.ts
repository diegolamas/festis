import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Festival } from './festival.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Festival>;

@Injectable()
export class FestivalService {

    private resourceUrl =  SERVER_API_URL + 'api/festivals';

    constructor(private http: HttpClient) { }

    create(festival: Festival): Observable<EntityResponseType> {
        const copy = this.convert(festival);
        return this.http.post<Festival>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(festival: Festival): Observable<EntityResponseType> {
        const copy = this.convert(festival);
        return this.http.put<Festival>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Festival>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Festival[]>> {
        const options = createRequestOption(req);
        return this.http.get<Festival[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Festival[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Festival = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Festival[]>): HttpResponse<Festival[]> {
        const jsonResponse: Festival[] = res.body;
        const body: Festival[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Festival.
     */
    private convertItemFromServer(festival: Festival): Festival {
        const copy: Festival = Object.assign({}, festival);
        return copy;
    }

    /**
     * Convert a Festival to a JSON which can be sent to the server.
     */
    private convert(festival: Festival): Festival {
        const copy: Festival = Object.assign({}, festival);
        return copy;
    }
}
