import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Abonne } from './abonne.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Abonne>;

@Injectable()
export class AbonneService {

    private resourceUrl =  SERVER_API_URL + 'api/abonnes';

    constructor(private http: HttpClient) { }

    create(abonne: Abonne): Observable<EntityResponseType> {
        const copy = this.convert(abonne);
        return this.http.post<Abonne>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(abonne: Abonne): Observable<EntityResponseType> {
        const copy = this.convert(abonne);
        return this.http.put<Abonne>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Abonne>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Abonne[]>> {
        const options = createRequestOption(req);
        return this.http.get<Abonne[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Abonne[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Abonne = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Abonne[]>): HttpResponse<Abonne[]> {
        const jsonResponse: Abonne[] = res.body;
        const body: Abonne[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Abonne.
     */
    private convertItemFromServer(abonne: Abonne): Abonne {
        const copy: Abonne = Object.assign({}, abonne);
        return copy;
    }

    /**
     * Convert a Abonne to a JSON which can be sent to the server.
     */
    private convert(abonne: Abonne): Abonne {
        const copy: Abonne = Object.assign({}, abonne);
        return copy;
    }
}
