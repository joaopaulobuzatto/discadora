import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IResponsavelCliente, ResponsavelCliente } from '../responsavel-cliente.model';
import { ResponsavelClienteService } from '../service/responsavel-cliente.service';
import { ICliente } from 'app/entities/cliente/cliente.model';
import { ClienteService } from 'app/entities/cliente/service/cliente.service';

@Component({
  selector: 'jhi-responsavel-cliente-update',
  templateUrl: './responsavel-cliente-update.component.html',
})
export class ResponsavelClienteUpdateComponent implements OnInit {
  isSaving = false;

  clientesSharedCollection: ICliente[] = [];

  editForm = this.fb.group({
    id: [],
    cpf: [],
    nome: [],
    dataNascimento: [],
    rg: [],
    telefone1: [],
    telefone2: [],
    email: [],
    nomeMae: [],
    nomePai: [],
    funcao: [],
    cliente: [],
  });

  constructor(
    protected responsavelClienteService: ResponsavelClienteService,
    protected clienteService: ClienteService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ responsavelCliente }) => {
      this.updateForm(responsavelCliente);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const responsavelCliente = this.createFromForm();
    if (responsavelCliente.id !== undefined) {
      this.subscribeToSaveResponse(this.responsavelClienteService.update(responsavelCliente));
    } else {
      this.subscribeToSaveResponse(this.responsavelClienteService.create(responsavelCliente));
    }
  }

  trackClienteById(index: number, item: ICliente): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IResponsavelCliente>>): void {
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

  protected updateForm(responsavelCliente: IResponsavelCliente): void {
    this.editForm.patchValue({
      id: responsavelCliente.id,
      cpf: responsavelCliente.cpf,
      nome: responsavelCliente.nome,
      dataNascimento: responsavelCliente.dataNascimento,
      rg: responsavelCliente.rg,
      telefone1: responsavelCliente.telefone1,
      telefone2: responsavelCliente.telefone2,
      email: responsavelCliente.email,
      nomeMae: responsavelCliente.nomeMae,
      nomePai: responsavelCliente.nomePai,
      funcao: responsavelCliente.funcao,
      cliente: responsavelCliente.cliente,
    });

    this.clientesSharedCollection = this.clienteService.addClienteToCollectionIfMissing(
      this.clientesSharedCollection,
      responsavelCliente.cliente
    );
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

  protected createFromForm(): IResponsavelCliente {
    return {
      ...new ResponsavelCliente(),
      id: this.editForm.get(['id'])!.value,
      cpf: this.editForm.get(['cpf'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      dataNascimento: this.editForm.get(['dataNascimento'])!.value,
      rg: this.editForm.get(['rg'])!.value,
      telefone1: this.editForm.get(['telefone1'])!.value,
      telefone2: this.editForm.get(['telefone2'])!.value,
      email: this.editForm.get(['email'])!.value,
      nomeMae: this.editForm.get(['nomeMae'])!.value,
      nomePai: this.editForm.get(['nomePai'])!.value,
      funcao: this.editForm.get(['funcao'])!.value,
      cliente: this.editForm.get(['cliente'])!.value,
    };
  }
}
