jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { FilialService } from '../service/filial.service';
import { IFilial, Filial } from '../filial.model';

import { FilialUpdateComponent } from './filial-update.component';

describe('Filial Management Update Component', () => {
  let comp: FilialUpdateComponent;
  let fixture: ComponentFixture<FilialUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let filialService: FilialService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [FilialUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(FilialUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FilialUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    filialService = TestBed.inject(FilialService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const filial: IFilial = { id: 456 };

      activatedRoute.data = of({ filial });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(filial));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Filial>>();
      const filial = { id: 123 };
      jest.spyOn(filialService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ filial });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: filial }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(filialService.update).toHaveBeenCalledWith(filial);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Filial>>();
      const filial = new Filial();
      jest.spyOn(filialService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ filial });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: filial }));
      saveSubject.complete();

      // THEN
      expect(filialService.create).toHaveBeenCalledWith(filial);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Filial>>();
      const filial = { id: 123 };
      jest.spyOn(filialService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ filial });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(filialService.update).toHaveBeenCalledWith(filial);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
