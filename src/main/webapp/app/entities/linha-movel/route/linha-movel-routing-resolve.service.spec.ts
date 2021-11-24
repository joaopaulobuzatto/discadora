jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ILinhaMovel, LinhaMovel } from '../linha-movel.model';
import { LinhaMovelService } from '../service/linha-movel.service';

import { LinhaMovelRoutingResolveService } from './linha-movel-routing-resolve.service';

describe('LinhaMovel routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: LinhaMovelRoutingResolveService;
  let service: LinhaMovelService;
  let resultLinhaMovel: ILinhaMovel | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(LinhaMovelRoutingResolveService);
    service = TestBed.inject(LinhaMovelService);
    resultLinhaMovel = undefined;
  });

  describe('resolve', () => {
    it('should return ILinhaMovel returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultLinhaMovel = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultLinhaMovel).toEqual({ id: 123 });
    });

    it('should return new ILinhaMovel if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultLinhaMovel = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultLinhaMovel).toEqual(new LinhaMovel());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as LinhaMovel })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultLinhaMovel = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultLinhaMovel).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
