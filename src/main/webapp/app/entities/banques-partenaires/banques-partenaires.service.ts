import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { BanquesPartenaires } from './banques-partenaires.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<BanquesPartenaires>;

@Injectable()
export class BanquesPartenairesService {

    private resourceUrl =  SERVER_API_URL + 'api/banques-partenaires';

    constructor(private http: HttpClient) { }

    create(banquesPartenaires: BanquesPartenaires): Observable<EntityResponseType> {
        const copy = this.convert(banquesPartenaires);
        return this.http.post<BanquesPartenaires>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(banquesPartenaires: BanquesPartenaires): Observable<EntityResponseType> {
        const copy = this.convert(banquesPartenaires);
        return this.http.put<BanquesPartenaires>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<BanquesPartenaires>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<BanquesPartenaires[]>> {
        const options = createRequestOption(req);
        return this.http.get<BanquesPartenaires[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<BanquesPartenaires[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: BanquesPartenaires = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<BanquesPartenaires[]>): HttpResponse<BanquesPartenaires[]> {
        const jsonResponse: BanquesPartenaires[] = res.body;
        const body: BanquesPartenaires[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to BanquesPartenaires.
     */
    private convertItemFromServer(banquesPartenaires: BanquesPartenaires): BanquesPartenaires {
        const copy: BanquesPartenaires = Object.assign({}, banquesPartenaires);
        return copy;
    }

    /**
     * Convert a BanquesPartenaires to a JSON which can be sent to the server.
     */
    private convert(banquesPartenaires: BanquesPartenaires): BanquesPartenaires {
        const copy: BanquesPartenaires = Object.assign({}, banquesPartenaires);
        return copy;
    }
}
