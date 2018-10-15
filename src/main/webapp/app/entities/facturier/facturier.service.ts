import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Facturier } from './facturier.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Facturier>;

@Injectable()
export class FacturierService {

    private resourceUrl =  SERVER_API_URL + 'api/facturiers';

    constructor(private http: HttpClient) { }

    create(facturier: Facturier): Observable<EntityResponseType> {
        const copy = this.convert(facturier);
        return this.http.post<Facturier>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(facturier: Facturier): Observable<EntityResponseType> {
        const copy = this.convert(facturier);
        return this.http.put<Facturier>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Facturier>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Facturier[]>> {
        const options = createRequestOption(req);
        return this.http.get<Facturier[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Facturier[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Facturier = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Facturier[]>): HttpResponse<Facturier[]> {
        const jsonResponse: Facturier[] = res.body;
        const body: Facturier[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Facturier.
     */
    private convertItemFromServer(facturier: Facturier): Facturier {
        const copy: Facturier = Object.assign({}, facturier);
        return copy;
    }

    /**
     * Convert a Facturier to a JSON which can be sent to the server.
     */
    private convert(facturier: Facturier): Facturier {
        const copy: Facturier = Object.assign({}, facturier);
        return copy;
    }
}
