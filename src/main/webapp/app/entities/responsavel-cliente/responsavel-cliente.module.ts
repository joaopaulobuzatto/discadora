import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ResponsavelClienteComponent } from './list/responsavel-cliente.component';
import { ResponsavelClienteDetailComponent } from './detail/responsavel-cliente-detail.component';
import { ResponsavelClienteUpdateComponent } from './update/responsavel-cliente-update.component';
import { ResponsavelClienteDeleteDialogComponent } from './delete/responsavel-cliente-delete-dialog.component';
import { ResponsavelClienteRoutingModule } from './route/responsavel-cliente-routing.module';

@NgModule({
  imports: [SharedModule, ResponsavelClienteRoutingModule],
  declarations: [
    ResponsavelClienteComponent,
    ResponsavelClienteDetailComponent,
    ResponsavelClienteUpdateComponent,
    ResponsavelClienteDeleteDialogComponent,
  ],
  entryComponents: [ResponsavelClienteDeleteDialogComponent],
})
export class ResponsavelClienteModule {}
