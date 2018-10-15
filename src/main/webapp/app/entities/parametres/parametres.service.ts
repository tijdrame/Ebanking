import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Parametres } from './parametres.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Parametres>;

@Injectable()
export class ParametresService {

    private resourceUrl =  SERVER_API_URL + 'api/parametres';

    constructor(private http: HttpClient) { }

    create(parametres: Parametres): Observable<EntityResponseType> {
        const copy = this.convert(parametres);
        return this.http.post<Parametres>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(parametres: Parametres): Observable<EntityResponseType> {
        const copy = this.convert(parametres);
        return this.http.put<Parametres>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Parametres>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Parametres[]>> {
        const options = createRequestOption(req);
        return this.http.get<Parametres[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Parametres[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Parametres = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Parametres[]>): HttpResponse<Parametres[]> {
        const jsonResponse: Parametres[] = res.body;
        const body: Parametres[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Parametres.
     */
    private convertItemFromServer(parametres: Parametres): Parametres {
        const copy: Parametres = Object.assign({}, parametres);
        return copy;
    }

    /**
     * Convert a Parametres to a JSON which can be sent to the server.
     */
    private convert(parametres: Parametres): Parametres {
        const copy: Parametres = Object.assign({}, parametres);
        return copy;
    }
}
