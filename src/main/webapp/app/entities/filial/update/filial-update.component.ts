import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IFilial, Filial } from '../filial.model';
import { FilialService } from '../service/filial.service';

@Component({
  selector: 'jhi-filial-update',
  templateUrl: './filial-update.component.html',
})
export class FilialUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    cnpj: [],
    razaoSocial: [],
    nomeFantasia: [],
  });

  constructor(protected filialService: FilialService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ filial }) => {
      this.updateForm(filial);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const filial = this.createFromForm();
    if (filial.id !== undefined) {
      this.subscribeToSaveResponse(this.filialService.update(filial));
    } else {
      this.subscribeToSaveResponse(this.filialService.create(filial));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFilial>>): void {
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

  protected updateForm(filial: IFilial): void {
    this.editForm.patchValue({
      id: filial.id,
      cnpj: filial.cnpj,
      razaoSocial: filial.razaoSocial,
      nomeFantasia: filial.nomeFantasia,
    });
  }

  protected createFromForm(): IFilial {
    return {
      ...new Filial(),
      id: this.editForm.get(['id'])!.value,
      cnpj: this.editForm.get(['cnpj'])!.value,
      razaoSocial: this.editForm.get(['razaoSocial'])!.value,
      nomeFantasia: this.editForm.get(['nomeFantasia'])!.value,
    };
  }
}
