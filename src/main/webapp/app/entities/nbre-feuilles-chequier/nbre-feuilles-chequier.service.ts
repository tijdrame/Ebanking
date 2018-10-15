import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { NbreFeuillesChequier } from './nbre-feuilles-chequier.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<NbreFeuillesChequier>;

@Injectable()
export class NbreFeuillesChequierService {

    private resourceUrl =  SERVER_API_URL + 'api/nbre-feuilles-chequiers';

    constructor(private http: HttpClient) { }

    create(nbreFeuillesChequier: NbreFeuillesChequier): Observable<EntityResponseType> {
        const copy = this.convert(nbreFeuillesChequier);
        return this.http.post<NbreFeuillesChequier>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(nbreFeuillesChequier: NbreFeuillesChequier): Observable<EntityResponseType> {
        const copy = this.convert(nbreFeuillesChequier);
        return this.http.put<NbreFeuillesChequier>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<NbreFeuillesChequier>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<NbreFeuillesChequier[]>> {
        const options = createRequestOption(req);
        return this.http.get<NbreFeuillesChequier[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<NbreFeuillesChequier[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: NbreFeuillesChequier = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<NbreFeuillesChequier[]>): HttpResponse<NbreFeuillesChequier[]> {
        const jsonResponse: NbreFeuillesChequier[] = res.body;
        const body: NbreFeuillesChequier[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to NbreFeuillesChequier.
     */
    private convertItemFromServer(nbreFeuillesChequier: NbreFeuillesChequier): NbreFeuillesChequier {
        const copy: NbreFeuillesChequier = Object.assign({}, nbreFeuillesChequier);
        return copy;
    }

    /**
     * Convert a NbreFeuillesChequier to a JSON which can be sent to the server.
     */
    private convert(nbreFeuillesChequier: NbreFeuillesChequier): NbreFeuillesChequier {
        const copy: NbreFeuillesChequier = Object.assign({}, nbreFeuillesChequier);
        return copy;
    }
}
