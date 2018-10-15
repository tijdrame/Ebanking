import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Devise } from './devise.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Devise>;

@Injectable()
export class DeviseService {

    private resourceUrl =  SERVER_API_URL + 'api/devises';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(devise: Devise): Observable<EntityResponseType> {
        const copy = this.convert(devise);
        return this.http.post<Devise>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(devise: Devise): Observable<EntityResponseType> {
        const copy = this.convert(devise);
        return this.http.put<Devise>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Devise>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Devise[]>> {
        const options = createRequestOption(req);
        return this.http.get<Devise[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Devise[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Devise = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Devise[]>): HttpResponse<Devise[]> {
        const jsonResponse: Devise[] = res.body;
        const body: Devise[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Devise.
     */
    private convertItemFromServer(devise: Devise): Devise {
        const copy: Devise = Object.assign({}, devise);
        copy.dateMaj = this.dateUtils
            .convertLocalDateFromServer(devise.dateMaj);
        return copy;
    }

    /**
     * Convert a Devise to a JSON which can be sent to the server.
     */
    private convert(devise: Devise): Devise {
        const copy: Devise = Object.assign({}, devise);
        copy.dateMaj = this.dateUtils
            .convertLocalDateToServer(devise.dateMaj);
        return copy;
    }
}
