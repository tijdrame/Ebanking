import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { PriseEnCharge } from './prise-en-charge.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<PriseEnCharge>;

@Injectable()
export class PriseEnChargeService {

    private resourceUrl =  SERVER_API_URL + 'api/prise-en-charges';

    constructor(private http: HttpClient) { }

    create(priseEnCharge: PriseEnCharge): Observable<EntityResponseType> {
        const copy = this.convert(priseEnCharge);
        return this.http.post<PriseEnCharge>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(priseEnCharge: PriseEnCharge): Observable<EntityResponseType> {
        const copy = this.convert(priseEnCharge);
        return this.http.put<PriseEnCharge>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<PriseEnCharge>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<PriseEnCharge[]>> {
        const options = createRequestOption(req);
        return this.http.get<PriseEnCharge[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<PriseEnCharge[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: PriseEnCharge = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<PriseEnCharge[]>): HttpResponse<PriseEnCharge[]> {
        const jsonResponse: PriseEnCharge[] = res.body;
        const body: PriseEnCharge[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to PriseEnCharge.
     */
    private convertItemFromServer(priseEnCharge: PriseEnCharge): PriseEnCharge {
        const copy: PriseEnCharge = Object.assign({}, priseEnCharge);
        return copy;
    }

    /**
     * Convert a PriseEnCharge to a JSON which can be sent to the server.
     */
    private convert(priseEnCharge: PriseEnCharge): PriseEnCharge {
        const copy: PriseEnCharge = Object.assign({}, priseEnCharge);
        return copy;
    }
}
