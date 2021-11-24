import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { LinhaMovelComponent } from '../list/linha-movel.component';
import { LinhaMovelDetailComponent } from '../detail/linha-movel-detail.component';
import { LinhaMovelUpdateComponent } from '../update/linha-movel-update.component';
import { LinhaMovelRoutingResolveService } from './linha-movel-routing-resolve.service';

const linhaMovelRoute: Routes = [
  {
    path: '',
    component: LinhaMovelComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: LinhaMovelDetailComponent,
    resolve: {
      linhaMovel: LinhaMovelRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: LinhaMovelUpdateComponent,
    resolve: {
      linhaMovel: LinhaMovelRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: LinhaMovelUpdateComponent,
    resolve: {
      linhaMovel: LinhaMovelRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(linhaMovelRoute)],
  exports: [RouterModule],
})
export class LinhaMovelRoutingModule {}
