import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Beneficiaire } from './beneficiaire.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Beneficiaire>;

@Injectable()
export class BeneficiaireService {

    private resourceUrl =  SERVER_API_URL + 'api/beneficiaires';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(beneficiaire: Beneficiaire): Observable<EntityResponseType> {
        const copy = this.convert(beneficiaire);
        return this.http.post<Beneficiaire>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(beneficiaire: Beneficiaire): Observable<EntityResponseType> {
        const copy = this.convert(beneficiaire);
        return this.http.put<Beneficiaire>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Beneficiaire>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Beneficiaire[]>> {
        const options = createRequestOption(req);
        return this.http.get<Beneficiaire[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Beneficiaire[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Beneficiaire = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Beneficiaire[]>): HttpResponse<Beneficiaire[]> {
        const jsonResponse: Beneficiaire[] = res.body;
        const body: Beneficiaire[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Beneficiaire.
     */
    private convertItemFromServer(beneficiaire: Beneficiaire): Beneficiaire {
        const copy: Beneficiaire = Object.assign({}, beneficiaire);
        copy.dateDemande = this.dateUtils
            .convertLocalDateFromServer(beneficiaire.dateDemande);
        copy.dateAcceptation = this.dateUtils
            .convertLocalDateFromServer(beneficiaire.dateAcceptation);
        return copy;
    }

    /**
     * Convert a Beneficiaire to a JSON which can be sent to the server.
     */
    private convert(beneficiaire: Beneficiaire): Beneficiaire {
        const copy: Beneficiaire = Object.assign({}, beneficiaire);
        copy.dateDemande = this.dateUtils
            .convertLocalDateToServer(beneficiaire.dateDemande);
        copy.dateAcceptation = this.dateUtils
            .convertLocalDateToServer(beneficiaire.dateAcceptation);
        return copy;
    }
}
