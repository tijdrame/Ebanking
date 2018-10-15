import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { TypeAbonne } from './type-abonne.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<TypeAbonne>;

@Injectable()
export class TypeAbonneService {

    private resourceUrl =  SERVER_API_URL + 'api/type-abonnes';

    constructor(private http: HttpClient) { }

    create(typeAbonne: TypeAbonne): Observable<EntityResponseType> {
        const copy = this.convert(typeAbonne);
        return this.http.post<TypeAbonne>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(typeAbonne: TypeAbonne): Observable<EntityResponseType> {
        const copy = this.convert(typeAbonne);
        return this.http.put<TypeAbonne>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<TypeAbonne>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<TypeAbonne[]>> {
        const options = createRequestOption(req);
        return this.http.get<TypeAbonne[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<TypeAbonne[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: TypeAbonne = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<TypeAbonne[]>): HttpResponse<TypeAbonne[]> {
        const jsonResponse: TypeAbonne[] = res.body;
        const body: TypeAbonne[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to TypeAbonne.
     */
    private convertItemFromServer(typeAbonne: TypeAbonne): TypeAbonne {
        const copy: TypeAbonne = Object.assign({}, typeAbonne);
        return copy;
    }

    /**
     * Convert a TypeAbonne to a JSON which can be sent to the server.
     */
    private convert(typeAbonne: TypeAbonne): TypeAbonne {
        const copy: TypeAbonne = Object.assign({}, typeAbonne);
        return copy;
    }
}
