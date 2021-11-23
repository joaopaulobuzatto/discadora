import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ILinhaMovel, LinhaMovel } from '../linha-movel.model';
import { LinhaMovelService } from '../service/linha-movel.service';
import { ICliente } from 'app/entities/cliente/cliente.model';
import { ClienteService } from 'app/entities/cliente/service/cliente.service';

@Component({
  selector: 'jhi-linha-movel-update',
  templateUrl: './linha-movel-update.component.html',
})
export class LinhaMovelUpdateComponent implements OnInit {
  isSaving = false;

  clientesSharedCollection: ICliente[] = [];

  editForm = this.fb.group({
    id: [],
    numeroLinha: [],
    status: [],
    dataAtivacao: [],
    plano: [],
    operadora: [],
    cliente: [],
  });

  constructor(
    protected linhaMovelService: LinhaMovelService,
    protected clienteService: ClienteService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ linhaMovel }) => {
      this.updateForm(linhaMovel);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const linhaMovel = this.createFromForm();
    if (linhaMovel.id !== undefined) {
      this.subscribeToSaveResponse(this.linhaMovelService.update(linhaMovel));
    } else {
      this.subscribeToSaveResponse(this.linhaMovelService.create(linhaMovel));
    }
  }

  trackClienteById(index: number, item: ICliente): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILinhaMovel>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(linhaMovel: ILinhaMovel): void {
    this.editForm.patchValue({
      id: linhaMovel.id,
      numeroLinha: linhaMovel.numeroLinha,
      status: linhaMovel.status,
      dataAtivacao: linhaMovel.dataAtivacao,
      plano: linhaMovel.plano,
      operadora: linhaMovel.operadora,
      cliente: linhaMovel.cliente,
    });

    this.clientesSharedCollection = this.clienteService.addClienteToCollectionIfMissing(this.clientesSharedCollection, linhaMovel.cliente);
  }

  protected loadRelationshipsOptions(): void {
    this.clienteService
      .query()
      .pipe(map((res: HttpResponse<ICliente[]>) => res.body ?? []))
      .pipe(
        map((clientes: ICliente[]) => this.clienteService.addClienteToCollectionIfMissing(clientes, this.editForm.get('cliente')!.value))
      )
      .subscribe((clientes: ICliente[]) => (this.clientesSharedCollection = clientes));
  }

  protected createFromForm(): ILinhaMovel {
    return {
      ...new LinhaMovel(),
      id: this.editForm.get(['id'])!.value,
      numeroLinha: this.editForm.get(['numeroLinha'])!.value,
      status: this.editForm.get(['status'])!.value,
      dataAtivacao: this.editForm.get(['dataAtivacao'])!.value,
      plano: this.editForm.get(['plano'])!.value,
      operadora: this.editForm.get(['operadora'])!.value,
      cliente: this.editForm.get(['cliente'])!.value,
    };
  }
}
