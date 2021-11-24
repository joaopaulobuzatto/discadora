import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { FaturaComponent } from './list/fatura.component';
import { FaturaDetailComponent } from './detail/fatura-detail.component';
import { FaturaUpdateComponent } from './update/fatura-update.component';
import { FaturaDeleteDialogComponent } from './delete/fatura-delete-dialog.component';
import { FaturaRoutingModule } from './route/fatura-routing.module';

@NgModule({
  imports: [SharedModule, FaturaRoutingModule],
  declarations: [FaturaComponent, FaturaDetailComponent, FaturaUpdateComponent, FaturaDeleteDialogComponent],
  entryComponents: [FaturaDeleteDialogComponent],
})
export class FaturaModule {}
