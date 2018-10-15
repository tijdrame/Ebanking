import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Agence } from './agence.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Agence>;

@Injectable()
export class AgenceService {

    private resourceUrl =  SERVER_API_URL + 'api/agences';

    constructor(private http: HttpClient) { }

    create(agence: Agence): Observable<EntityResponseType> {
        const copy = this.convert(agence);
        return this.http.post<Agence>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(agence: Agence): Observable<EntityResponseType> {
        const copy = this.convert(agence);
        return this.http.put<Agence>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Agence>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Agence[]>> {
        const options = createRequestOption(req);
        return this.http.get<Agence[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Agence[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Agence = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Agence[]>): HttpResponse<Agence[]> {
        const jsonResponse: Agence[] = res.body;
        const body: Agence[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Agence.
     */
    private convertItemFromServer(agence: Agence): Agence {
        const copy: Agence = Object.assign({}, agence);
        return copy;
    }

    /**
     * Convert a Agence to a JSON which can be sent to the server.
     */
    private convert(agence: Agence): Agence {
        const copy: Agence = Object.assign({}, agence);
        return copy;
    }
}
