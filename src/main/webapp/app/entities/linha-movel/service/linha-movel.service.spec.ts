import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { ILinhaMovel, LinhaMovel } from '../linha-movel.model';

import { LinhaMovelService } from './linha-movel.service';

describe('LinhaMovel Service', () => {
  let service: LinhaMovelService;
  let httpMock: HttpTestingController;
  let elemDefault: ILinhaMovel;
  let expectedResult: ILinhaMovel | ILinhaMovel[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(LinhaMovelService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      numeroLinha: 'AAAAAAA',
      status: 'AAAAAAA',
      dataAtivacao: currentDate,
      plano: 'AAAAAAA',
      operadora: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          dataAtivacao: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a LinhaMovel', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          dataAtivacao: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dataAtivacao: currentDate,
        },
        returnedFromService
      );

      service.create(new LinhaMovel()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a LinhaMovel', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          numeroLinha: 'BBBBBB',
          status: 'BBBBBB',
          dataAtivacao: currentDate.format(DATE_FORMAT),
          plano: 'BBBBBB',
          operadora: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dataAtivacao: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a LinhaMovel', () => {
      const patchObject = Object.assign(
        {
          status: 'BBBBBB',
          dataAtivacao: currentDate.format(DATE_FORMAT),
          operadora: 'BBBBBB',
        },
        new LinhaMovel()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          dataAtivacao: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of LinhaMovel', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          numeroLinha: 'BBBBBB',
          status: 'BBBBBB',
          dataAtivacao: currentDate.format(DATE_FORMAT),
          plano: 'BBBBBB',
          operadora: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dataAtivacao: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a LinhaMovel', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addLinhaMovelToCollectionIfMissing', () => {
      it('should add a LinhaMovel to an empty array', () => {
        const linhaMovel: ILinhaMovel = { id: 123 };
        expectedResult = service.addLinhaMovelToCollectionIfMissing([], linhaMovel);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(linhaMovel);
      });

      it('should not add a LinhaMovel to an array that contains it', () => {
        const linhaMovel: ILinhaMovel = { id: 123 };
        const linhaMovelCollection: ILinhaMovel[] = [
          {
            ...linhaMovel,
          },
          { id: 456 },
        ];
        expectedResult = service.addLinhaMovelToCollectionIfMissing(linhaMovelCollection, linhaMovel);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a LinhaMovel to an array that doesn't contain it", () => {
        const linhaMovel: ILinhaMovel = { id: 123 };
        const linhaMovelCollection: ILinhaMovel[] = [{ id: 456 }];
        expectedResult = service.addLinhaMovelToCollectionIfMissing(linhaMovelCollection, linhaMovel);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(linhaMovel);
      });

      it('should add only unique LinhaMovel to an array', () => {
        const linhaMovelArray: ILinhaMovel[] = [{ id: 123 }, { id: 456 }, { id: 52139 }];
        const linhaMovelCollection: ILinhaMovel[] = [{ id: 123 }];
        expectedResult = service.addLinhaMovelToCollectionIfMissing(linhaMovelCollection, ...linhaMovelArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const linhaMovel: ILinhaMovel = { id: 123 };
        const linhaMovel2: ILinhaMovel = { id: 456 };
        expectedResult = service.addLinhaMovelToCollectionIfMissing([], linhaMovel, linhaMovel2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(linhaMovel);
        expect(expectedResult).toContain(linhaMovel2);
      });

      it('should accept null and undefined values', () => {
        const linhaMovel: ILinhaMovel = { id: 123 };
        expectedResult = service.addLinhaMovelToCollectionIfMissing([], null, linhaMovel, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(linhaMovel);
      });

      it('should return initial array if no LinhaMovel is added', () => {
        const linhaMovelCollection: ILinhaMovel[] = [{ id: 123 }];
        expectedResult = service.addLinhaMovelToCollectionIfMissing(linhaMovelCollection, undefined, null);
        expect(expectedResult).toEqual(linhaMovelCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
