import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IResponsavelCliente, ResponsavelCliente } from '../responsavel-cliente.model';

import { ResponsavelClienteService } from './responsavel-cliente.service';

describe('ResponsavelCliente Service', () => {
  let service: ResponsavelClienteService;
  let httpMock: HttpTestingController;
  let elemDefault: IResponsavelCliente;
  let expectedResult: IResponsavelCliente | IResponsavelCliente[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ResponsavelClienteService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      cpf: 'AAAAAAA',
      nome: 'AAAAAAA',
      dataNascimento: currentDate,
      rg: 'AAAAAAA',
      telefone1: 'AAAAAAA',
      telefone2: 'AAAAAAA',
      email: 'AAAAAAA',
      nomeMae: 'AAAAAAA',
      nomePai: 'AAAAAAA',
      funcao: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          dataNascimento: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a ResponsavelCliente', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          dataNascimento: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dataNascimento: currentDate,
        },
        returnedFromService
      );

      service.create(new ResponsavelCliente()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ResponsavelCliente', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          cpf: 'BBBBBB',
          nome: 'BBBBBB',
          dataNascimento: currentDate.format(DATE_FORMAT),
          rg: 'BBBBBB',
          telefone1: 'BBBBBB',
          telefone2: 'BBBBBB',
          email: 'BBBBBB',
          nomeMae: 'BBBBBB',
          nomePai: 'BBBBBB',
          funcao: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dataNascimento: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ResponsavelCliente', () => {
      const patchObject = Object.assign(
        {
          cpf: 'BBBBBB',
          dataNascimento: currentDate.format(DATE_FORMAT),
          rg: 'BBBBBB',
          telefone2: 'BBBBBB',
          funcao: 'BBBBBB',
        },
        new ResponsavelCliente()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          dataNascimento: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ResponsavelCliente', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          cpf: 'BBBBBB',
          nome: 'BBBBBB',
          dataNascimento: currentDate.format(DATE_FORMAT),
          rg: 'BBBBBB',
          telefone1: 'BBBBBB',
          telefone2: 'BBBBBB',
          email: 'BBBBBB',
          nomeMae: 'BBBBBB',
          nomePai: 'BBBBBB',
          funcao: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dataNascimento: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a ResponsavelCliente', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addResponsavelClienteToCollectionIfMissing', () => {
      it('should add a ResponsavelCliente to an empty array', () => {
        const responsavelCliente: IResponsavelCliente = { id: 123 };
        expectedResult = service.addResponsavelClienteToCollectionIfMissing([], responsavelCliente);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(responsavelCliente);
      });

      it('should not add a ResponsavelCliente to an array that contains it', () => {
        const responsavelCliente: IResponsavelCliente = { id: 123 };
        const responsavelClienteCollection: IResponsavelCliente[] = [
          {
            ...responsavelCliente,
          },
          { id: 456 },
        ];
        expectedResult = service.addResponsavelClienteToCollectionIfMissing(responsavelClienteCollection, responsavelCliente);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ResponsavelCliente to an array that doesn't contain it", () => {
        const responsavelCliente: IResponsavelCliente = { id: 123 };
        const responsavelClienteCollection: IResponsavelCliente[] = [{ id: 456 }];
        expectedResult = service.addResponsavelClienteToCollectionIfMissing(responsavelClienteCollection, responsavelCliente);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(responsavelCliente);
      });

      it('should add only unique ResponsavelCliente to an array', () => {
        const responsavelClienteArray: IResponsavelCliente[] = [{ id: 123 }, { id: 456 }, { id: 3605 }];
        const responsavelClienteCollection: IResponsavelCliente[] = [{ id: 123 }];
        expectedResult = service.addResponsavelClienteToCollectionIfMissing(responsavelClienteCollection, ...responsavelClienteArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const responsavelCliente: IResponsavelCliente = { id: 123 };
        const responsavelCliente2: IResponsavelCliente = { id: 456 };
        expectedResult = service.addResponsavelClienteToCollectionIfMissing([], responsavelCliente, responsavelCliente2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(responsavelCliente);
        expect(expectedResult).toContain(responsavelCliente2);
      });

      it('should accept null and undefined values', () => {
        const responsavelCliente: IResponsavelCliente = { id: 123 };
        expectedResult = service.addResponsavelClienteToCollectionIfMissing([], null, responsavelCliente, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(responsavelCliente);
      });

      it('should return initial array if no ResponsavelCliente is added', () => {
        const responsavelClienteCollection: IResponsavelCliente[] = [{ id: 123 }];
        expectedResult = service.addResponsavelClienteToCollectionIfMissing(responsavelClienteCollection, undefined, null);
        expect(expectedResult).toEqual(responsavelClienteCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
