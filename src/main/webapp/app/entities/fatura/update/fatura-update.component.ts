import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IFatura, Fatura } from '../fatura.model';
import { FaturaService } from '../service/fatura.service';
import { ICliente } from 'app/entities/cliente/cliente.model';
import { ClienteService } from 'app/entities/cliente/service/cliente.service';

@Component({
  selector: 'jhi-fatura-update',
  templateUrl: './fatura-update.component.html',
})
export class FaturaUpdateComponent implements OnInit {
  isSaving = false;

  clientesSharedCollection: ICliente[] = [];

  editForm = this.fb.group({
    id: [],
    numeroFatura: [],
    dataVencimento: [],
    valorFaturado: [],
    valorAberto: [],
    status: [],
    cliente: [],
  });

  constructor(
    protected faturaService: FaturaService,
    protected clienteService: ClienteService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fatura }) => {
      this.updateForm(fatura);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const fatura = this.createFromForm();
    if (fatura.id !== undefined) {
      this.subscribeToSaveResponse(this.faturaService.update(fatura));
    } else {
      this.subscribeToSaveResponse(this.faturaService.create(fatura));
    }
  }

  trackClienteById(index: number, item: ICliente): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFatura>>): void {
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

  protected updateForm(fatura: IFatura): void {
    this.editForm.patchValue({
      id: fatura.id,
      numeroFatura: fatura.numeroFatura,
      dataVencimento: fatura.dataVencimento,
      valorFaturado: fatura.valorFaturado,
      valorAberto: fatura.valorAberto,
      status: fatura.status,
      cliente: fatura.cliente,
    });

    this.clientesSharedCollection = this.clienteService.addClienteToCollectionIfMissing(this.clientesSharedCollection, fatura.cliente);
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

  protected createFromForm(): IFatura {
    return {
      ...new Fatura(),
      id: this.editForm.get(['id'])!.value,
      numeroFatura: this.editForm.get(['numeroFatura'])!.value,
      dataVencimento: this.editForm.get(['dataVencimento'])!.value,
      valorFaturado: this.editForm.get(['valorFaturado'])!.value,
      valorAberto: this.editForm.get(['valorAberto'])!.value,
      status: this.editForm.get(['status'])!.value,
      cliente: this.editForm.get(['cliente'])!.value,
    };
  }
}
