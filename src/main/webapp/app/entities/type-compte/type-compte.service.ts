import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { TypeCompte } from './type-compte.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<TypeCompte>;

@Injectable()
export class TypeCompteService {

    private resourceUrl =  SERVER_API_URL + 'api/type-comptes';

    constructor(private http: HttpClient) { }

    create(typeCompte: TypeCompte): Observable<EntityResponseType> {
        const copy = this.convert(typeCompte);
        return this.http.post<TypeCompte>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(typeCompte: TypeCompte): Observable<EntityResponseType> {
        const copy = this.convert(typeCompte);
        return this.http.put<TypeCompte>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<TypeCompte>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<TypeCompte[]>> {
        const options = createRequestOption(req);
        return this.http.get<TypeCompte[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<TypeCompte[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: TypeCompte = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<TypeCompte[]>): HttpResponse<TypeCompte[]> {
        const jsonResponse: TypeCompte[] = res.body;
        const body: TypeCompte[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to TypeCompte.
     */
    private convertItemFromServer(typeCompte: TypeCompte): TypeCompte {
        const copy: TypeCompte = Object.assign({}, typeCompte);
        return copy;
    }

    /**
     * Convert a TypeCompte to a JSON which can be sent to the server.
     */
    private convert(typeCompte: TypeCompte): TypeCompte {
        const copy: TypeCompte = Object.assign({}, typeCompte);
        return copy;
    }
}
