jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IResponsavelCliente, ResponsavelCliente } from '../responsavel-cliente.model';
import { ResponsavelClienteService } from '../service/responsavel-cliente.service';

import { ResponsavelClienteRoutingResolveService } from './responsavel-cliente-routing-resolve.service';

describe('ResponsavelCliente routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: ResponsavelClienteRoutingResolveService;
  let service: ResponsavelClienteService;
  let resultResponsavelCliente: IResponsavelCliente | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(ResponsavelClienteRoutingResolveService);
    service = TestBed.inject(ResponsavelClienteService);
    resultResponsavelCliente = undefined;
  });

  describe('resolve', () => {
    it('should return IResponsavelCliente returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultResponsavelCliente = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultResponsavelCliente).toEqual({ id: 123 });
    });

    it('should return new IResponsavelCliente if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultResponsavelCliente = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultResponsavelCliente).toEqual(new ResponsavelCliente());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as ResponsavelCliente })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultResponsavelCliente = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultResponsavelCliente).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
