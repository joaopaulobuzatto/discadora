import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IFatura } from '../fatura.model';
import { FaturaService } from '../service/fatura.service';

@Component({
  templateUrl: './fatura-delete-dialog.component.html',
})
export class FaturaDeleteDialogComponent {
  fatura?: IFatura;

  constructor(protected faturaService: FaturaService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.faturaService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
