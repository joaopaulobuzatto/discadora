import * as dayjs from 'dayjs';

export interface IFilial {
  id?: number;
  cnpj?: string | null;
  razaoSocial?: string | null;
  nomeFantasia?: string | null;
  createdBy?: string | null;
  createdDate?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  lastModifiedDate?: dayjs.Dayjs | null;
}

export class Filial implements IFilial {
  constructor(
    public id?: number,
    public cnpj?: string | null,
    public razaoSocial?: string | null,
    public nomeFantasia?: string | null,
    public createdBy?: string | null,
    public createdDate?: dayjs.Dayjs | null,
    public lastModifiedBy?: string | null,
    public lastModifiedDate?: dayjs.Dayjs | null
  ) {}
}

export function getFilialIdentifier(filial: IFilial): number | undefined {
  return filial.id;
}
