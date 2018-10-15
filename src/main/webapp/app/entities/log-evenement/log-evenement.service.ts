import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { LogEvenement } from './log-evenement.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<LogEvenement>;

@Injectable()
export class LogEvenementService {

    private resourceUrl =  SERVER_API_URL + 'api/log-evenements';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(logEvenement: LogEvenement): Observable<EntityResponseType> {
        const copy = this.convert(logEvenement);
        return this.http.post<LogEvenement>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(logEvenement: LogEvenement): Observable<EntityResponseType> {
        const copy = this.convert(logEvenement);
        return this.http.put<LogEvenement>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<LogEvenement>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<LogEvenement[]>> {
        const options = createRequestOption(req);
        return this.http.get<LogEvenement[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<LogEvenement[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: LogEvenement = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<LogEvenement[]>): HttpResponse<LogEvenement[]> {
        const jsonResponse: LogEvenement[] = res.body;
        const body: LogEvenement[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to LogEvenement.
     */
    private convertItemFromServer(logEvenement: LogEvenement): LogEvenement {
        const copy: LogEvenement = Object.assign({}, logEvenement);
        copy.dateCreated = this.dateUtils
            .convertDateTimeFromServer(logEvenement.dateCreated);
        return copy;
    }

    /**
     * Convert a LogEvenement to a JSON which can be sent to the server.
     */
    private convert(logEvenement: LogEvenement): LogEvenement {
        const copy: LogEvenement = Object.assign({}, logEvenement);

        copy.dateCreated = this.dateUtils.toDate(logEvenement.dateCreated);
        return copy;
    }
}
