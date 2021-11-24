import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ILinhaMovel, getLinhaMovelIdentifier } from '../linha-movel.model';

export type EntityResponseType = HttpResponse<ILinhaMovel>;
export type EntityArrayResponseType = HttpResponse<ILinhaMovel[]>;

@Injectable({ providedIn: 'root' })
export class LinhaMovelService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/linha-movels');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(linhaMovel: ILinhaMovel): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(linhaMovel);
    return this.http
      .post<ILinhaMovel>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(linhaMovel: ILinhaMovel): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(linhaMovel);
    return this.http
      .put<ILinhaMovel>(`${this.resourceUrl}/${getLinhaMovelIdentifier(linhaMovel) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(linhaMovel: ILinhaMovel): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(linhaMovel);
    return this.http
      .patch<ILinhaMovel>(`${this.resourceUrl}/${getLinhaMovelIdentifier(linhaMovel) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ILinhaMovel>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ILinhaMovel[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addLinhaMovelToCollectionIfMissing(
    linhaMovelCollection: ILinhaMovel[],
    ...linhaMovelsToCheck: (ILinhaMovel | null | undefined)[]
  ): ILinhaMovel[] {
    const linhaMovels: ILinhaMovel[] = linhaMovelsToCheck.filter(isPresent);
    if (linhaMovels.length > 0) {
      const linhaMovelCollectionIdentifiers = linhaMovelCollection.map(linhaMovelItem => getLinhaMovelIdentifier(linhaMovelItem)!);
      const linhaMovelsToAdd = linhaMovels.filter(linhaMovelItem => {
        const linhaMovelIdentifier = getLinhaMovelIdentifier(linhaMovelItem);
        if (linhaMovelIdentifier == null || linhaMovelCollectionIdentifiers.includes(linhaMovelIdentifier)) {
          return false;
        }
        linhaMovelCollectionIdentifiers.push(linhaMovelIdentifier);
        return true;
      });
      return [...linhaMovelsToAdd, ...linhaMovelCollection];
    }
    return linhaMovelCollection;
  }

  protected convertDateFromClient(linhaMovel: ILinhaMovel): ILinhaMovel {
    return Object.assign({}, linhaMovel, {
      dataAtivacao: linhaMovel.dataAtivacao?.isValid() ? linhaMovel.dataAtivacao.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dataAtivacao = res.body.dataAtivacao ? dayjs(res.body.dataAtivacao) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((linhaMovel: ILinhaMovel) => {
        linhaMovel.dataAtivacao = linhaMovel.dataAtivacao ? dayjs(linhaMovel.dataAtivacao) : undefined;
      });
    }
    return res;
  }
}
