import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IResponsavelCliente } from '../responsavel-cliente.model';
import { ResponsavelClienteService } from '../service/responsavel-cliente.service';

@Component({
  templateUrl: './responsavel-cliente-delete-dialog.component.html',
})
export class ResponsavelClienteDeleteDialogComponent {
  responsavelCliente?: IResponsavelCliente;

  constructor(protected responsavelClienteService: ResponsavelClienteService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.responsavelClienteService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
