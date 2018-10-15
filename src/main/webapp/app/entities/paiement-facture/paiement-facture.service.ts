import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { PaiementFacture } from './paiement-facture.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<PaiementFacture>;

@Injectable()
export class PaiementFactureService {

    private resourceUrl =  SERVER_API_URL + 'api/paiement-factures';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(paiementFacture: PaiementFacture): Observable<EntityResponseType> {
        const copy = this.convert(paiementFacture);
        return this.http.post<PaiementFacture>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(paiementFacture: PaiementFacture): Observable<EntityResponseType> {
        const copy = this.convert(paiementFacture);
        return this.http.put<PaiementFacture>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<PaiementFacture>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<PaiementFacture[]>> {
        const options = createRequestOption(req);
        return this.http.get<PaiementFacture[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<PaiementFacture[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: PaiementFacture = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<PaiementFacture[]>): HttpResponse<PaiementFacture[]> {
        const jsonResponse: PaiementFacture[] = res.body;
        const body: PaiementFacture[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to PaiementFacture.
     */
    private convertItemFromServer(paiementFacture: PaiementFacture): PaiementFacture {
        const copy: PaiementFacture = Object.assign({}, paiementFacture);
        copy.dateDebut = this.dateUtils
            .convertLocalDateFromServer(paiementFacture.dateDebut);
        copy.dateFin = this.dateUtils
            .convertLocalDateFromServer(paiementFacture.dateFin);
        copy.dateAcceptation = this.dateUtils
            .convertLocalDateFromServer(paiementFacture.dateAcceptation);
        copy.dateDemande = this.dateUtils
            .convertLocalDateFromServer(paiementFacture.dateDemande);
        return copy;
    }

    /**
     * Convert a PaiementFacture to a JSON which can be sent to the server.
     */
    private convert(paiementFacture: PaiementFacture): PaiementFacture {
        const copy: PaiementFacture = Object.assign({}, paiementFacture);
        copy.dateDebut = this.dateUtils
            .convertLocalDateToServer(paiementFacture.dateDebut);
        copy.dateFin = this.dateUtils
            .convertLocalDateToServer(paiementFacture.dateFin);
        copy.dateAcceptation = this.dateUtils
            .convertLocalDateToServer(paiementFacture.dateAcceptation);
        copy.dateDemande = this.dateUtils
            .convertLocalDateToServer(paiementFacture.dateDemande);
        return copy;
    }
}
