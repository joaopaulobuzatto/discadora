import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFatura, getFaturaIdentifier } from '../fatura.model';

export type EntityResponseType = HttpResponse<IFatura>;
export type EntityArrayResponseType = HttpResponse<IFatura[]>;

@Injectable({ providedIn: 'root' })
export class FaturaService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/faturas');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(fatura: IFatura): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(fatura);
    return this.http
      .post<IFatura>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(fatura: IFatura): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(fatura);
    return this.http
      .put<IFatura>(`${this.resourceUrl}/${getFaturaIdentifier(fatura) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(fatura: IFatura): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(fatura);
    return this.http
      .patch<IFatura>(`${this.resourceUrl}/${getFaturaIdentifier(fatura) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IFatura>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFatura[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addFaturaToCollectionIfMissing(faturaCollection: IFatura[], ...faturasToCheck: (IFatura | null | undefined)[]): IFatura[] {
    const faturas: IFatura[] = faturasToCheck.filter(isPresent);
    if (faturas.length > 0) {
      const faturaCollectionIdentifiers = faturaCollection.map(faturaItem => getFaturaIdentifier(faturaItem)!);
      const faturasToAdd = faturas.filter(faturaItem => {
        const faturaIdentifier = getFaturaIdentifier(faturaItem);
        if (faturaIdentifier == null || faturaCollectionIdentifiers.includes(faturaIdentifier)) {
          return false;
        }
        faturaCollectionIdentifiers.push(faturaIdentifier);
        return true;
      });
      return [...faturasToAdd, ...faturaCollection];
    }
    return faturaCollection;
  }

  protected convertDateFromClient(fatura: IFatura): IFatura {
    return Object.assign({}, fatura, {
      dataVencimento: fatura.dataVencimento?.isValid() ? fatura.dataVencimento.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dataVencimento = res.body.dataVencimento ? dayjs(res.body.dataVencimento) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((fatura: IFatura) => {
        fatura.dataVencimento = fatura.dataVencimento ? dayjs(fatura.dataVencimento) : undefined;
      });
    }
    return res;
  }
}
