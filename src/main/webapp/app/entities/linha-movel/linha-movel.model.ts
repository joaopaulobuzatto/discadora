import * as dayjs from 'dayjs';
import { ICliente } from 'app/entities/cliente/cliente.model';

export interface ILinhaMovel {
  id?: number;
  numeroLinha?: string | null;
  status?: string | null;
  dataAtivacao?: dayjs.Dayjs | null;
  plano?: string | null;
  operadora?: string | null;
  cliente?: ICliente | null;
}

export class LinhaMovel implements ILinhaMovel {
  constructor(
    public id?: number,
    public numeroLinha?: string | null,
    public status?: string | null,
    public dataAtivacao?: dayjs.Dayjs | null,
    public plano?: string | null,
    public operadora?: string | null,
    public cliente?: ICliente | null
  ) {}
}

export function getLinhaMovelIdentifier(linhaMovel: ILinhaMovel): number | undefined {
  return linhaMovel.id;
}
