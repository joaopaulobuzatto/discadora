jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IFatura, Fatura } from '../fatura.model';
import { FaturaService } from '../service/fatura.service';

import { FaturaRoutingResolveService } from './fatura-routing-resolve.service';

describe('Fatura routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: FaturaRoutingResolveService;
  let service: FaturaService;
  let resultFatura: IFatura | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(FaturaRoutingResolveService);
    service = TestBed.inject(FaturaService);
    resultFatura = undefined;
  });

  describe('resolve', () => {
    it('should return IFatura returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultFatura = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultFatura).toEqual({ id: 123 });
    });

    it('should return new IFatura if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultFatura = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultFatura).toEqual(new Fatura());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as Fatura })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultFatura = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultFatura).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
