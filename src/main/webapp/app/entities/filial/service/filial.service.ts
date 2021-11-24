import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFilial, getFilialIdentifier } from '../filial.model';

export type EntityResponseType = HttpResponse<IFilial>;
export type EntityArrayResponseType = HttpResponse<IFilial[]>;

@Injectable({ providedIn: 'root' })
export class FilialService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/filials');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(filial: IFilial): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(filial);
    return this.http
      .post<IFilial>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(filial: IFilial): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(filial);
    return this.http
      .put<IFilial>(`${this.resourceUrl}/${getFilialIdentifier(filial) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(filial: IFilial): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(filial);
    return this.http
      .patch<IFilial>(`${this.resourceUrl}/${getFilialIdentifier(filial) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IFilial>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFilial[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addFilialToCollectionIfMissing(filialCollection: IFilial[], ...filialsToCheck: (IFilial | null | undefined)[]): IFilial[] {
    const filials: IFilial[] = filialsToCheck.filter(isPresent);
    if (filials.length > 0) {
      const filialCollectionIdentifiers = filialCollection.map(filialItem => getFilialIdentifier(filialItem)!);
      const filialsToAdd = filials.filter(filialItem => {
        const filialIdentifier = getFilialIdentifier(filialItem);
        if (filialIdentifier == null || filialCollectionIdentifiers.includes(filialIdentifier)) {
          return false;
        }
        filialCollectionIdentifiers.push(filialIdentifier);
        return true;
      });
      return [...filialsToAdd, ...filialCollection];
    }
    return filialCollection;
  }

  protected convertDateFromClient(filial: IFilial): IFilial {
    return Object.assign({}, filial, {
      createdDate: filial.createdDate?.isValid() ? filial.createdDate.toJSON() : undefined,
      lastModifiedDate: filial.lastModifiedDate?.isValid() ? filial.lastModifiedDate.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdDate = res.body.createdDate ? dayjs(res.body.createdDate) : undefined;
      res.body.lastModifiedDate = res.body.lastModifiedDate ? dayjs(res.body.lastModifiedDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((filial: IFilial) => {
        filial.createdDate = filial.createdDate ? dayjs(filial.createdDate) : undefined;
        filial.lastModifiedDate = filial.lastModifiedDate ? dayjs(filial.lastModifiedDate) : undefined;
      });
    }
    return res;
  }
}
