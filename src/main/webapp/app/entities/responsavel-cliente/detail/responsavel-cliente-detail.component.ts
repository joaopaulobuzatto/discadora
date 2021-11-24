import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IResponsavelCliente } from '../responsavel-cliente.model';

@Component({
  selector: 'jhi-responsavel-cliente-detail',
  templateUrl: './responsavel-cliente-detail.component.html',
})
export class ResponsavelClienteDetailComponent implements OnInit {
  responsavelCliente: IResponsavelCliente | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ responsavelCliente }) => {
      this.responsavelCliente = responsavelCliente;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
