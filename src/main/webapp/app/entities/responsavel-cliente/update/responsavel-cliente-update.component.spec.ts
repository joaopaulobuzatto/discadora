jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { ResponsavelClienteService } from '../service/responsavel-cliente.service';
import { IResponsavelCliente, ResponsavelCliente } from '../responsavel-cliente.model';
import { ICliente } from 'app/entities/cliente/cliente.model';
import { ClienteService } from 'app/entities/cliente/service/cliente.service';

import { ResponsavelClienteUpdateComponent } from './responsavel-cliente-update.component';

describe('ResponsavelCliente Management Update Component', () => {
  let comp: ResponsavelClienteUpdateComponent;
  let fixture: ComponentFixture<ResponsavelClienteUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let responsavelClienteService: ResponsavelClienteService;
  let clienteService: ClienteService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [ResponsavelClienteUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(ResponsavelClienteUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ResponsavelClienteUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    responsavelClienteService = TestBed.inject(ResponsavelClienteService);
    clienteService = TestBed.inject(ClienteService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Cliente query and add missing value', () => {
      const responsavelCliente: IResponsavelCliente = { id: 456 };
      const cliente: ICliente = { id: 92060 };
      responsavelCliente.cliente = cliente;

      const clienteCollection: ICliente[] = [{ id: 42705 }];
      jest.spyOn(clienteService, 'query').mockReturnValue(of(new HttpResponse({ body: clienteCollection })));
      const additionalClientes = [cliente];
      const expectedCollection: ICliente[] = [...additionalClientes, ...clienteCollection];
      jest.spyOn(clienteService, 'addClienteToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ responsavelCliente });
      comp.ngOnInit();

      expect(clienteService.query).toHaveBeenCalled();
      expect(clienteService.addClienteToCollectionIfMissing).toHaveBeenCalledWith(clienteCollection, ...additionalClientes);
      expect(comp.clientesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const responsavelCliente: IResponsavelCliente = { id: 456 };
      const cliente: ICliente = { id: 96262 };
      responsavelCliente.cliente = cliente;

      activatedRoute.data = of({ responsavelCliente });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(responsavelCliente));
      expect(comp.clientesSharedCollection).toContain(cliente);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ResponsavelCliente>>();
      const responsavelCliente = { id: 123 };
      jest.spyOn(responsavelClienteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ responsavelCliente });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: responsavelCliente }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(responsavelClienteService.update).toHaveBeenCalledWith(responsavelCliente);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ResponsavelCliente>>();
      const responsavelCliente = new ResponsavelCliente();
      jest.spyOn(responsavelClienteService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ responsavelCliente });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: responsavelCliente }));
      saveSubject.complete();

      // THEN
      expect(responsavelClienteService.create).toHaveBeenCalledWith(responsavelCliente);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ResponsavelCliente>>();
      const responsavelCliente = { id: 123 };
      jest.spyOn(responsavelClienteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ responsavelCliente });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(responsavelClienteService.update).toHaveBeenCalledWith(responsavelCliente);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackClienteById', () => {
      it('Should return tracked Cliente primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackClienteById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
