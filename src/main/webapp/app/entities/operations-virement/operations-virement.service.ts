import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { OperationsVirement } from './operations-virement.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<OperationsVirement>;

@Injectable()
export class OperationsVirementService {

    private resourceUrl =  SERVER_API_URL + 'api/operations-virements';

    constructor(private http: HttpClient) { }

    create(operationsVirement: OperationsVirement): Observable<EntityResponseType> {
        const copy = this.convert(operationsVirement);
        return this.http.post<OperationsVirement>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(operationsVirement: OperationsVirement): Observable<EntityResponseType> {
        const copy = this.convert(operationsVirement);
        return this.http.put<OperationsVirement>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<OperationsVirement>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<OperationsVirement[]>> {
        const options = createRequestOption(req);
        return this.http.get<OperationsVirement[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<OperationsVirement[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: OperationsVirement = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<OperationsVirement[]>): HttpResponse<OperationsVirement[]> {
        const jsonResponse: OperationsVirement[] = res.body;
        const body: OperationsVirement[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to OperationsVirement.
     */
    private convertItemFromServer(operationsVirement: OperationsVirement): OperationsVirement {
        const copy: OperationsVirement = Object.assign({}, operationsVirement);
        return copy;
    }

    /**
     * Convert a OperationsVirement to a JSON which can be sent to the server.
     */
    private convert(operationsVirement: OperationsVirement): OperationsVirement {
        const copy: OperationsVirement = Object.assign({}, operationsVirement);
        return copy;
    }
}
