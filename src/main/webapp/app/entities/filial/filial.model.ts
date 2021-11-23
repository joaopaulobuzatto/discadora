export interface IFilial {
  id?: number;
  cnpj?: string | null;
  razaoSocial?: string | null;
  nomeFantasia?: string | null;
}

export class Filial implements IFilial {
  constructor(public id?: number, public cnpj?: string | null, public razaoSocial?: string | null, public nomeFantasia?: string | null) {}
}

export function getFilialIdentifier(filial: IFilial): number | undefined {
  return filial.id;
}
