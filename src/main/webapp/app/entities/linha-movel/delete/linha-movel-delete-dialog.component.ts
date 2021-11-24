import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ILinhaMovel } from '../linha-movel.model';
import { LinhaMovelService } from '../service/linha-movel.service';

@Component({
  templateUrl: './linha-movel-delete-dialog.component.html',
})
export class LinhaMovelDeleteDialogComponent {
  linhaMovel?: ILinhaMovel;

  constructor(protected linhaMovelService: LinhaMovelService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.linhaMovelService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
