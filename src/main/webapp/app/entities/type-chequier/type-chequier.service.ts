import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { TypeChequier } from './type-chequier.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<TypeChequier>;

@Injectable()
export class TypeChequierService {

    private resourceUrl =  SERVER_API_URL + 'api/type-chequiers';

    constructor(private http: HttpClient) { }

    create(typeChequier: TypeChequier): Observable<EntityResponseType> {
        const copy = this.convert(typeChequier);
        return this.http.post<TypeChequier>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(typeChequier: TypeChequier): Observable<EntityResponseType> {
        const copy = this.convert(typeChequier);
        return this.http.put<TypeChequier>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<TypeChequier>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<TypeChequier[]>> {
        const options = createRequestOption(req);
        return this.http.get<TypeChequier[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<TypeChequier[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: TypeChequier = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<TypeChequier[]>): HttpResponse<TypeChequier[]> {
        const jsonResponse: TypeChequier[] = res.body;
        const body: TypeChequier[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to TypeChequier.
     */
    private convertItemFromServer(typeChequier: TypeChequier): TypeChequier {
        const copy: TypeChequier = Object.assign({}, typeChequier);
        return copy;
    }

    /**
     * Convert a TypeChequier to a JSON which can be sent to the server.
     */
    private convert(typeChequier: TypeChequier): TypeChequier {
        const copy: TypeChequier = Object.assign({}, typeChequier);
        return copy;
    }
}
