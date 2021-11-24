import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { LinhaMovelComponent } from './list/linha-movel.component';
import { LinhaMovelDetailComponent } from './detail/linha-movel-detail.component';
import { LinhaMovelUpdateComponent } from './update/linha-movel-update.component';
import { LinhaMovelDeleteDialogComponent } from './delete/linha-movel-delete-dialog.component';
import { LinhaMovelRoutingModule } from './route/linha-movel-routing.module';

@NgModule({
  imports: [SharedModule, LinhaMovelRoutingModule],
  declarations: [LinhaMovelComponent, LinhaMovelDetailComponent, LinhaMovelUpdateComponent, LinhaMovelDeleteDialogComponent],
  entryComponents: [LinhaMovelDeleteDialogComponent],
})
export class LinhaMovelModule {}
