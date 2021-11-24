import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ResponsavelClienteComponent } from '../list/responsavel-cliente.component';
import { ResponsavelClienteDetailComponent } from '../detail/responsavel-cliente-detail.component';
import { ResponsavelClienteUpdateComponent } from '../update/responsavel-cliente-update.component';
import { ResponsavelClienteRoutingResolveService } from './responsavel-cliente-routing-resolve.service';

const responsavelClienteRoute: Routes = [
  {
    path: '',
    component: ResponsavelClienteComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ResponsavelClienteDetailComponent,
    resolve: {
      responsavelCliente: ResponsavelClienteRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ResponsavelClienteUpdateComponent,
    resolve: {
      responsavelCliente: ResponsavelClienteRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ResponsavelClienteUpdateComponent,
    resolve: {
      responsavelCliente: ResponsavelClienteRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(responsavelClienteRoute)],
  exports: [RouterModule],
})
export class ResponsavelClienteRoutingModule {}
