import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Edition } from './edition.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Edition>;

@Injectable()
export class EditionService {

    private resourceUrl =  SERVER_API_URL + 'api/editions';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(edition: Edition): Observable<EntityResponseType> {
        const copy = this.convert(edition);
        return this.http.post<Edition>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(edition: Edition): Observable<EntityResponseType> {
        const copy = this.convert(edition);
        return this.http.put<Edition>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Edition>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Edition[]>> {
        const options = createRequestOption(req);
        return this.http.get<Edition[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Edition[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Edition = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Edition[]>): HttpResponse<Edition[]> {
        const jsonResponse: Edition[] = res.body;
        const body: Edition[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Edition.
     */
    private convertItemFromServer(edition: Edition): Edition {
        const copy: Edition = Object.assign({}, edition);
        copy.startDate = this.dateUtils
            .convertLocalDateFromServer(edition.startDate);
        copy.endDate = this.dateUtils
            .convertLocalDateFromServer(edition.endDate);
        return copy;
    }

    /**
     * Convert a Edition to a JSON which can be sent to the server.
     */
    private convert(edition: Edition): Edition {
        const copy: Edition = Object.assign({}, edition);
        copy.startDate = this.dateUtils
            .convertLocalDateToServer(edition.startDate);
        copy.endDate = this.dateUtils
            .convertLocalDateToServer(edition.endDate);
        return copy;
    }
}
