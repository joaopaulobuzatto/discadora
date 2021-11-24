import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ICliente, Cliente } from '../cliente.model';
import { ClienteService } from '../service/cliente.service';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { IFilial } from 'app/entities/filial/filial.model';
import { FilialService } from 'app/entities/filial/service/filial.service';

@Component({
  selector: 'jhi-cliente-update',
  templateUrl: './cliente-update.component.html',
})
export class ClienteUpdateComponent implements OnInit {
  isSaving = false;

  usersSharedCollection: IUser[] = [];
  filialsSharedCollection: IFilial[] = [];

  editForm = this.fb.group({
    id: [],
    cnpj: [],
    razaoSocial: [],
    nomeFantasia: [],
    telefone1: [],
    telefone2: [],
    telefone3: [],
    telefone4: [],
    telefone5: [],
    telefone6: [],
    telefone7: [],
    telefone8: [],
    email1: [],
    email2: [],
    email3: [],
    email4: [],
    email5: [],
    email6: [],
    quantidadeFuncionarios: [],
    quantidadeLinhasMovel: [],
    quantidadeLinhasFixa: [],
    nomeContatoPrincipal: [],
    telefone1ContatoPrincipal: [],
    telefone2ContatoPrincipal: [],
    emailContatoPrincipal: [],
    observacao: [],
    cep: [],
    logradouro: [],
    bairro: [],
    numero: [],
    cidade: [],
    uf: [],
    createdBy: [],
    createdDate: [],
    lastModifiedBy: [],
    lastModifiedDate: [],
    consultor: [],
    supervisor: [],
    filial: [],
  });

  constructor(
    protected clienteService: ClienteService,
    protected userService: UserService,
    protected filialService: FilialService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cliente }) => {
      if (cliente.id === undefined) {
        const today = dayjs().startOf('day');
        cliente.createdDate = today;
        cliente.lastModifiedDate = today;
      }

      this.updateForm(cliente);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cliente = this.createFromForm();
    if (cliente.id !== undefined) {
      this.subscribeToSaveResponse(this.clienteService.update(cliente));
    } else {
      this.subscribeToSaveResponse(this.clienteService.create(cliente));
    }
  }

  trackUserById(index: number, item: IUser): number {
    return item.id!;
  }

  trackFilialById(index: number, item: IFilial): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICliente>>): void {
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

  protected updateForm(cliente: ICliente): void {
    this.editForm.patchValue({
      id: cliente.id,
      cnpj: cliente.cnpj,
      razaoSocial: cliente.razaoSocial,
      nomeFantasia: cliente.nomeFantasia,
      telefone1: cliente.telefone1,
      telefone2: cliente.telefone2,
      telefone3: cliente.telefone3,
      telefone4: cliente.telefone4,
      telefone5: cliente.telefone5,
      telefone6: cliente.telefone6,
      telefone7: cliente.telefone7,
      telefone8: cliente.telefone8,
      email1: cliente.email1,
      email2: cliente.email2,
      email3: cliente.email3,
      email4: cliente.email4,
      email5: cliente.email5,
      email6: cliente.email6,
      quantidadeFuncionarios: cliente.quantidadeFuncionarios,
      quantidadeLinhasMovel: cliente.quantidadeLinhasMovel,
      quantidadeLinhasFixa: cliente.quantidadeLinhasFixa,
      nomeContatoPrincipal: cliente.nomeContatoPrincipal,
      telefone1ContatoPrincipal: cliente.telefone1ContatoPrincipal,
      telefone2ContatoPrincipal: cliente.telefone2ContatoPrincipal,
      emailContatoPrincipal: cliente.emailContatoPrincipal,
      observacao: cliente.observacao,
      cep: cliente.cep,
      logradouro: cliente.logradouro,
      bairro: cliente.bairro,
      numero: cliente.numero,
      cidade: cliente.cidade,
      uf: cliente.uf,
      createdBy: cliente.createdBy,
      createdDate: cliente.createdDate ? cliente.createdDate.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: cliente.lastModifiedBy,
      lastModifiedDate: cliente.lastModifiedDate ? cliente.lastModifiedDate.format(DATE_TIME_FORMAT) : null,
      consultor: cliente.consultor,
      supervisor: cliente.supervisor,
      filial: cliente.filial,
    });

    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing(
      this.usersSharedCollection,
      cliente.consultor,
      cliente.supervisor
    );
    this.filialsSharedCollection = this.filialService.addFilialToCollectionIfMissing(this.filialsSharedCollection, cliente.filial);
  }

  protected loadRelationshipsOptions(): void {
    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(
        map((users: IUser[]) =>
          this.userService.addUserToCollectionIfMissing(
            users,
            this.editForm.get('consultor')!.value,
            this.editForm.get('supervisor')!.value
          )
        )
      )
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));

    this.filialService
      .query()
      .pipe(map((res: HttpResponse<IFilial[]>) => res.body ?? []))
      .pipe(map((filials: IFilial[]) => this.filialService.addFilialToCollectionIfMissing(filials, this.editForm.get('filial')!.value)))
      .subscribe((filials: IFilial[]) => (this.filialsSharedCollection = filials));
  }

  protected createFromForm(): ICliente {
    return {
      ...new Cliente(),
      id: this.editForm.get(['id'])!.value,
      cnpj: this.editForm.get(['cnpj'])!.value,
      razaoSocial: this.editForm.get(['razaoSocial'])!.value,
      nomeFantasia: this.editForm.get(['nomeFantasia'])!.value,
      telefone1: this.editForm.get(['telefone1'])!.value,
      telefone2: this.editForm.get(['telefone2'])!.value,
      telefone3: this.editForm.get(['telefone3'])!.value,
      telefone4: this.editForm.get(['telefone4'])!.value,
      telefone5: this.editForm.get(['telefone5'])!.value,
      telefone6: this.editForm.get(['telefone6'])!.value,
      telefone7: this.editForm.get(['telefone7'])!.value,
      telefone8: this.editForm.get(['telefone8'])!.value,
      email1: this.editForm.get(['email1'])!.value,
      email2: this.editForm.get(['email2'])!.value,
      email3: this.editForm.get(['email3'])!.value,
      email4: this.editForm.get(['email4'])!.value,
      email5: this.editForm.get(['email5'])!.value,
      email6: this.editForm.get(['email6'])!.value,
      quantidadeFuncionarios: this.editForm.get(['quantidadeFuncionarios'])!.value,
      quantidadeLinhasMovel: this.editForm.get(['quantidadeLinhasMovel'])!.value,
      quantidadeLinhasFixa: this.editForm.get(['quantidadeLinhasFixa'])!.value,
      nomeContatoPrincipal: this.editForm.get(['nomeContatoPrincipal'])!.value,
      telefone1ContatoPrincipal: this.editForm.get(['telefone1ContatoPrincipal'])!.value,
      telefone2ContatoPrincipal: this.editForm.get(['telefone2ContatoPrincipal'])!.value,
      emailContatoPrincipal: this.editForm.get(['emailContatoPrincipal'])!.value,
      observacao: this.editForm.get(['observacao'])!.value,
      cep: this.editForm.get(['cep'])!.value,
      logradouro: this.editForm.get(['logradouro'])!.value,
      bairro: this.editForm.get(['bairro'])!.value,
      numero: this.editForm.get(['numero'])!.value,
      cidade: this.editForm.get(['cidade'])!.value,
      uf: this.editForm.get(['uf'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdDate: this.editForm.get(['createdDate'])!.value
        ? dayjs(this.editForm.get(['createdDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      lastModifiedDate: this.editForm.get(['lastModifiedDate'])!.value
        ? dayjs(this.editForm.get(['lastModifiedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      consultor: this.editForm.get(['consultor'])!.value,
      supervisor: this.editForm.get(['supervisor'])!.value,
      filial: this.editForm.get(['filial'])!.value,
    };
  }
}
