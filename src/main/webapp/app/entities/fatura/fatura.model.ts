import * as dayjs from 'dayjs';
import { ICliente } from 'app/entities/cliente/cliente.model';

export interface IFatura {
  id?: number;
  numeroFatura?: string | null;
  dataVencimento?: dayjs.Dayjs | null;
  valorFaturado?: number | null;
  valorAberto?: number | null;
  status?: string | null;
  cliente?: ICliente | null;
}

export class Fatura implements IFatura {
  constructor(
    public id?: number,
    public numeroFatura?: string | null,
    public dataVencimento?: dayjs.Dayjs | null,
    public valorFaturado?: number | null,
    public valorAberto?: number | null,
    public status?: string | null,
    public cliente?: ICliente | null
  ) {}
}

export function getFaturaIdentifier(fatura: IFatura): number | undefined {
  return fatura.id;
}
