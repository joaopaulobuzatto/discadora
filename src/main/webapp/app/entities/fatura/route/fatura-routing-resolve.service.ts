import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFatura, Fatura } from '../fatura.model';
import { FaturaService } from '../service/fatura.service';

@Injectable({ providedIn: 'root' })
export class FaturaRoutingResolveService implements Resolve<IFatura> {
  constructor(protected service: FaturaService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFatura> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((fatura: HttpResponse<Fatura>) => {
          if (fatura.body) {
            return of(fatura.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Fatura());
  }
}
