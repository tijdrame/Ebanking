import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { OperationType } from './operation-type.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<OperationType>;

@Injectable()
export class OperationTypeService {

    private resourceUrl =  SERVER_API_URL + 'api/operation-types';

    constructor(private http: HttpClient) { }

    create(operationType: OperationType): Observable<EntityResponseType> {
        const copy = this.convert(operationType);
        return this.http.post<OperationType>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(operationType: OperationType): Observable<EntityResponseType> {
        const copy = this.convert(operationType);
        return this.http.put<OperationType>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<OperationType>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<OperationType[]>> {
        const options = createRequestOption(req);
        return this.http.get<OperationType[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<OperationType[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: OperationType = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<OperationType[]>): HttpResponse<OperationType[]> {
        const jsonResponse: OperationType[] = res.body;
        const body: OperationType[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to OperationType.
     */
    private convertItemFromServer(operationType: OperationType): OperationType {
        const copy: OperationType = Object.assign({}, operationType);
        return copy;
    }

    /**
     * Convert a OperationType to a JSON which can be sent to the server.
     */
    private convert(operationType: OperationType): OperationType {
        const copy: OperationType = Object.assign({}, operationType);
        return copy;
    }
}
