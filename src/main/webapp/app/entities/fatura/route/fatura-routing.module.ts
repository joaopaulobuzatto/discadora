import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { FaturaComponent } from '../list/fatura.component';
import { FaturaDetailComponent } from '../detail/fatura-detail.component';
import { FaturaUpdateComponent } from '../update/fatura-update.component';
import { FaturaRoutingResolveService } from './fatura-routing-resolve.service';

const faturaRoute: Routes = [
  {
    path: '',
    component: FaturaComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FaturaDetailComponent,
    resolve: {
      fatura: FaturaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FaturaUpdateComponent,
    resolve: {
      fatura: FaturaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FaturaUpdateComponent,
    resolve: {
      fatura: FaturaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(faturaRoute)],
  exports: [RouterModule],
})
export class FaturaRoutingModule {}
