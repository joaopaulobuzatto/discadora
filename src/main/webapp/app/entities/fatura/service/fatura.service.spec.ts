import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IFatura, Fatura } from '../fatura.model';

import { FaturaService } from './fatura.service';

describe('Fatura Service', () => {
  let service: FaturaService;
  let httpMock: HttpTestingController;
  let elemDefault: IFatura;
  let expectedResult: IFatura | IFatura[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(FaturaService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      numeroFatura: 'AAAAAAA',
      dataVencimento: currentDate,
      valorFaturado: 0,
      valorAberto: 0,
      status: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          dataVencimento: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a Fatura', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          dataVencimento: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dataVencimento: currentDate,
        },
        returnedFromService
      );

      service.create(new Fatura()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Fatura', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          numeroFatura: 'BBBBBB',
          dataVencimento: currentDate.format(DATE_FORMAT),
          valorFaturado: 1,
          valorAberto: 1,
          status: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dataVencimento: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Fatura', () => {
      const patchObject = Object.assign(
        {
          dataVencimento: currentDate.format(DATE_FORMAT),
          valorAberto: 1,
          status: 'BBBBBB',
        },
        new Fatura()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          dataVencimento: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Fatura', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          numeroFatura: 'BBBBBB',
          dataVencimento: currentDate.format(DATE_FORMAT),
          valorFaturado: 1,
          valorAberto: 1,
          status: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dataVencimento: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a Fatura', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addFaturaToCollectionIfMissing', () => {
      it('should add a Fatura to an empty array', () => {
        const fatura: IFatura = { id: 123 };
        expectedResult = service.addFaturaToCollectionIfMissing([], fatura);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(fatura);
      });

      it('should not add a Fatura to an array that contains it', () => {
        const fatura: IFatura = { id: 123 };
        const faturaCollection: IFatura[] = [
          {
            ...fatura,
          },
          { id: 456 },
        ];
        expectedResult = service.addFaturaToCollectionIfMissing(faturaCollection, fatura);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Fatura to an array that doesn't contain it", () => {
        const fatura: IFatura = { id: 123 };
        const faturaCollection: IFatura[] = [{ id: 456 }];
        expectedResult = service.addFaturaToCollectionIfMissing(faturaCollection, fatura);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(fatura);
      });

      it('should add only unique Fatura to an array', () => {
        const faturaArray: IFatura[] = [{ id: 123 }, { id: 456 }, { id: 65353 }];
        const faturaCollection: IFatura[] = [{ id: 123 }];
        expectedResult = service.addFaturaToCollectionIfMissing(faturaCollection, ...faturaArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const fatura: IFatura = { id: 123 };
        const fatura2: IFatura = { id: 456 };
        expectedResult = service.addFaturaToCollectionIfMissing([], fatura, fatura2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(fatura);
        expect(expectedResult).toContain(fatura2);
      });

      it('should accept null and undefined values', () => {
        const fatura: IFatura = { id: 123 };
        expectedResult = service.addFaturaToCollectionIfMissing([], null, fatura, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(fatura);
      });

      it('should return initial array if no Fatura is added', () => {
        const faturaCollection: IFatura[] = [{ id: 123 }];
        expectedResult = service.addFaturaToCollectionIfMissing(faturaCollection, undefined, null);
        expect(expectedResult).toEqual(faturaCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
