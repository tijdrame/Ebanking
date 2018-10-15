import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { TypeOpposition } from './type-opposition.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<TypeOpposition>;

@Injectable()
export class TypeOppositionService {

    private resourceUrl =  SERVER_API_URL + 'api/type-oppositions';

    constructor(private http: HttpClient) { }

    create(typeOpposition: TypeOpposition): Observable<EntityResponseType> {
        const copy = this.convert(typeOpposition);
        return this.http.post<TypeOpposition>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(typeOpposition: TypeOpposition): Observable<EntityResponseType> {
        const copy = this.convert(typeOpposition);
        return this.http.put<TypeOpposition>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<TypeOpposition>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<TypeOpposition[]>> {
        const options = createRequestOption(req);
        return this.http.get<TypeOpposition[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<TypeOpposition[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: TypeOpposition = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<TypeOpposition[]>): HttpResponse<TypeOpposition[]> {
        const jsonResponse: TypeOpposition[] = res.body;
        const body: TypeOpposition[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to TypeOpposition.
     */
    private convertItemFromServer(typeOpposition: TypeOpposition): TypeOpposition {
        const copy: TypeOpposition = Object.assign({}, typeOpposition);
        return copy;
    }

    /**
     * Convert a TypeOpposition to a JSON which can be sent to the server.
     */
    private convert(typeOpposition: TypeOpposition): TypeOpposition {
        const copy: TypeOpposition = Object.assign({}, typeOpposition);
        return copy;
    }
}
