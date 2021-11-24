import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IResponsavelCliente, ResponsavelCliente } from '../responsavel-cliente.model';
import { ResponsavelClienteService } from '../service/responsavel-cliente.service';

@Injectable({ providedIn: 'root' })
export class ResponsavelClienteRoutingResolveService implements Resolve<IResponsavelCliente> {
  constructor(protected service: ResponsavelClienteService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IResponsavelCliente> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((responsavelCliente: HttpResponse<ResponsavelCliente>) => {
          if (responsavelCliente.body) {
            return of(responsavelCliente.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ResponsavelCliente());
  }
}
