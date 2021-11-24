import * as dayjs from 'dayjs';
import { ICliente } from 'app/entities/cliente/cliente.model';

export interface IResponsavelCliente {
  id?: number;
  cpf?: string | null;
  nome?: string | null;
  dataNascimento?: dayjs.Dayjs | null;
  rg?: string | null;
  telefone1?: string | null;
  telefone2?: string | null;
  email?: string | null;
  nomeMae?: string | null;
  nomePai?: string | null;
  funcao?: string | null;
  createdBy?: string | null;
  createdDate?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  lastModifiedDate?: dayjs.Dayjs | null;
  cliente?: ICliente | null;
}

export class ResponsavelCliente implements IResponsavelCliente {
  constructor(
    public id?: number,
    public cpf?: string | null,
    public nome?: string | null,
    public dataNascimento?: dayjs.Dayjs | null,
    public rg?: string | null,
    public telefone1?: string | null,
    public telefone2?: string | null,
    public email?: string | null,
    public nomeMae?: string | null,
    public nomePai?: string | null,
    public funcao?: string | null,
    public createdBy?: string | null,
    public createdDate?: dayjs.Dayjs | null,
    public lastModifiedBy?: string | null,
    public lastModifiedDate?: dayjs.Dayjs | null,
    public cliente?: ICliente | null
  ) {}
}

export function getResponsavelClienteIdentifier(responsavelCliente: IResponsavelCliente): number | undefined {
  return responsavelCliente.id;
}
