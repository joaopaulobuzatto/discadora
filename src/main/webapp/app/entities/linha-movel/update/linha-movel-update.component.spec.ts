jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { LinhaMovelService } from '../service/linha-movel.service';
import { ILinhaMovel, LinhaMovel } from '../linha-movel.model';
import { ICliente } from 'app/entities/cliente/cliente.model';
import { ClienteService } from 'app/entities/cliente/service/cliente.service';

import { LinhaMovelUpdateComponent } from './linha-movel-update.component';

describe('LinhaMovel Management Update Component', () => {
  let comp: LinhaMovelUpdateComponent;
  let fixture: ComponentFixture<LinhaMovelUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let linhaMovelService: LinhaMovelService;
  let clienteService: ClienteService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [LinhaMovelUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(LinhaMovelUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(LinhaMovelUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    linhaMovelService = TestBed.inject(LinhaMovelService);
    clienteService = TestBed.inject(ClienteService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Cliente query and add missing value', () => {
      const linhaMovel: ILinhaMovel = { id: 456 };
      const cliente: ICliente = { id: 16411 };
      linhaMovel.cliente = cliente;

      const clienteCollection: ICliente[] = [{ id: 66240 }];
      jest.spyOn(clienteService, 'query').mockReturnValue(of(new HttpResponse({ body: clienteCollection })));
      const additionalClientes = [cliente];
      const expectedCollection: ICliente[] = [...additionalClientes, ...clienteCollection];
      jest.spyOn(clienteService, 'addClienteToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ linhaMovel });
      comp.ngOnInit();

      expect(clienteService.query).toHaveBeenCalled();
      expect(clienteService.addClienteToCollectionIfMissing).toHaveBeenCalledWith(clienteCollection, ...additionalClientes);
      expect(comp.clientesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const linhaMovel: ILinhaMovel = { id: 456 };
      const cliente: ICliente = { id: 27884 };
      linhaMovel.cliente = cliente;

      activatedRoute.data = of({ linhaMovel });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(linhaMovel));
      expect(comp.clientesSharedCollection).toContain(cliente);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<LinhaMovel>>();
      const linhaMovel = { id: 123 };
      jest.spyOn(linhaMovelService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ linhaMovel });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: linhaMovel }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(linhaMovelService.update).toHaveBeenCalledWith(linhaMovel);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<LinhaMovel>>();
      const linhaMovel = new LinhaMovel();
      jest.spyOn(linhaMovelService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ linhaMovel });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: linhaMovel }));
      saveSubject.complete();

      // THEN
      expect(linhaMovelService.create).toHaveBeenCalledWith(linhaMovel);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<LinhaMovel>>();
      const linhaMovel = { id: 123 };
      jest.spyOn(linhaMovelService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ linhaMovel });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(linhaMovelService.update).toHaveBeenCalledWith(linhaMovel);
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
