import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IResponsavelCliente, getResponsavelClienteIdentifier } from '../responsavel-cliente.model';

export type EntityResponseType = HttpResponse<IResponsavelCliente>;
export type EntityArrayResponseType = HttpResponse<IResponsavelCliente[]>;

@Injectable({ providedIn: 'root' })
export class ResponsavelClienteService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/responsavel-clientes');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(responsavelCliente: IResponsavelCliente): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(responsavelCliente);
    return this.http
      .post<IResponsavelCliente>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(responsavelCliente: IResponsavelCliente): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(responsavelCliente);
    return this.http
      .put<IResponsavelCliente>(`${this.resourceUrl}/${getResponsavelClienteIdentifier(responsavelCliente) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(responsavelCliente: IResponsavelCliente): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(responsavelCliente);
    return this.http
      .patch<IResponsavelCliente>(`${this.resourceUrl}/${getResponsavelClienteIdentifier(responsavelCliente) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IResponsavelCliente>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IResponsavelCliente[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addResponsavelClienteToCollectionIfMissing(
    responsavelClienteCollection: IResponsavelCliente[],
    ...responsavelClientesToCheck: (IResponsavelCliente | null | undefined)[]
  ): IResponsavelCliente[] {
    const responsavelClientes: IResponsavelCliente[] = responsavelClientesToCheck.filter(isPresent);
    if (responsavelClientes.length > 0) {
      const responsavelClienteCollectionIdentifiers = responsavelClienteCollection.map(
        responsavelClienteItem => getResponsavelClienteIdentifier(responsavelClienteItem)!
      );
      const responsavelClientesToAdd = responsavelClientes.filter(responsavelClienteItem => {
        const responsavelClienteIdentifier = getResponsavelClienteIdentifier(responsavelClienteItem);
        if (responsavelClienteIdentifier == null || responsavelClienteCollectionIdentifiers.includes(responsavelClienteIdentifier)) {
          return false;
        }
        responsavelClienteCollectionIdentifiers.push(responsavelClienteIdentifier);
        return true;
      });
      return [...responsavelClientesToAdd, ...responsavelClienteCollection];
    }
    return responsavelClienteCollection;
  }

  protected convertDateFromClient(responsavelCliente: IResponsavelCliente): IResponsavelCliente {
    return Object.assign({}, responsavelCliente, {
      dataNascimento: responsavelCliente.dataNascimento?.isValid() ? responsavelCliente.dataNascimento.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dataNascimento = res.body.dataNascimento ? dayjs(res.body.dataNascimento) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((responsavelCliente: IResponsavelCliente) => {
        responsavelCliente.dataNascimento = responsavelCliente.dataNascimento ? dayjs(responsavelCliente.dataNascimento) : undefined;
      });
    }
    return res;
  }
}
