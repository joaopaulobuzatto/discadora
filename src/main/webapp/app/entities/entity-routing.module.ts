import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'filial',
        data: { pageTitle: 'Filials' },
        loadChildren: () => import('./filial/filial.module').then(m => m.FilialModule),
      },
      {
        path: 'cliente',
        data: { pageTitle: 'Clientes' },
        loadChildren: () => import('./cliente/cliente.module').then(m => m.ClienteModule),
      },
      {
        path: 'fatura',
        data: { pageTitle: 'Faturas' },
        loadChildren: () => import('./fatura/fatura.module').then(m => m.FaturaModule),
      },
      {
        path: 'linha-movel',
        data: { pageTitle: 'LinhaMovels' },
        loadChildren: () => import('./linha-movel/linha-movel.module').then(m => m.LinhaMovelModule),
      },
      {
        path: 'responsavel-cliente',
        data: { pageTitle: 'ResponsavelClientes' },
        loadChildren: () => import('./responsavel-cliente/responsavel-cliente.module').then(m => m.ResponsavelClienteModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
