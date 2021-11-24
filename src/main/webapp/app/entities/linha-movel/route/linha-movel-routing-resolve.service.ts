import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ILinhaMovel, LinhaMovel } from '../linha-movel.model';
import { LinhaMovelService } from '../service/linha-movel.service';

@Injectable({ providedIn: 'root' })
export class LinhaMovelRoutingResolveService implements Resolve<ILinhaMovel> {
  constructor(protected service: LinhaMovelService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILinhaMovel> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((linhaMovel: HttpResponse<LinhaMovel>) => {
          if (linhaMovel.body) {
            return of(linhaMovel.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new LinhaMovel());
  }
}
