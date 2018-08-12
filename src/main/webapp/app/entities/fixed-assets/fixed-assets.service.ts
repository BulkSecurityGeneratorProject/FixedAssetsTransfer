import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IFixedAssets } from 'app/shared/model/fixed-assets.model';

type EntityResponseType = HttpResponse<IFixedAssets>;
type EntityArrayResponseType = HttpResponse<IFixedAssets[]>;

@Injectable({ providedIn: 'root' })
export class FixedAssetsService {
    private resourceUrl = SERVER_API_URL + 'api/fixed-assets';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/fixed-assets';

    constructor(private http: HttpClient) {}

    create(fixedAssets: IFixedAssets): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(fixedAssets);
        return this.http
            .post<IFixedAssets>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(fixedAssets: IFixedAssets): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(fixedAssets);
        return this.http
            .put<IFixedAssets>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IFixedAssets>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IFixedAssets[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IFixedAssets[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    private convertDateFromClient(fixedAssets: IFixedAssets): IFixedAssets {
        const copy: IFixedAssets = Object.assign({}, fixedAssets, {
            date: fixedAssets.date != null && fixedAssets.date.isValid() ? fixedAssets.date.format(DATE_FORMAT) : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.date = res.body.date != null ? moment(res.body.date) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((fixedAssets: IFixedAssets) => {
            fixedAssets.date = fixedAssets.date != null ? moment(fixedAssets.date) : null;
        });
        return res;
    }
}
