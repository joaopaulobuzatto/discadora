import { IUser } from 'app/entities/user/user.model';
import { IFilial } from 'app/entities/filial/filial.model';

export interface ICliente {
  id?: number;
  cnpj?: string | null;
  razaoSocial?: string | null;
  nomeFantasia?: string | null;
  telefone1?: string | null;
  telefone2?: string | null;
  telefone3?: string | null;
  telefone4?: string | null;
  telefone5?: string | null;
  telefone6?: string | null;
  telefone7?: string | null;
  telefone8?: string | null;
  email1?: string | null;
  email2?: string | null;
  email3?: string | null;
  email4?: string | null;
  email5?: string | null;
  email6?: string | null;
  quantidadeFuncionarios?: number | null;
  quantidadeLinhasMovel?: number | null;
  quantidadeLinhasFixa?: number | null;
  nomeContatoPrincipal?: string | null;
  telefone1ContatoPrincipal?: string | null;
  telefone2ContatoPrincipal?: string | null;
  emailContatoPrincipal?: string | null;
  observacao?: string | null;
  cep?: string | null;
  logradouro?: string | null;
  bairro?: string | null;
  numero?: string | null;
  cidade?: string | null;
  uf?: string | null;
  consultor?: IUser | null;
  supervisor?: IUser | null;
  filial?: IFilial | null;
}

export class Cliente implements ICliente {
  constructor(
    public id?: number,
    public cnpj?: string | null,
    public razaoSocial?: string | null,
    public nomeFantasia?: string | null,
    public telefone1?: string | null,
    public telefone2?: string | null,
    public telefone3?: string | null,
    public telefone4?: string | null,
    public telefone5?: string | null,
    public telefone6?: string | null,
    public telefone7?: string | null,
    public telefone8?: string | null,
    public email1?: string | null,
    public email2?: string | null,
    public email3?: string | null,
    public email4?: string | null,
    public email5?: string | null,
    public email6?: string | null,
    public quantidadeFuncionarios?: number | null,
    public quantidadeLinhasMovel?: number | null,
    public quantidadeLinhasFixa?: number | null,
    public nomeContatoPrincipal?: string | null,
    public telefone1ContatoPrincipal?: string | null,
    public telefone2ContatoPrincipal?: string | null,
    public emailContatoPrincipal?: string | null,
    public observacao?: string | null,
    public cep?: string | null,
    public logradouro?: string | null,
    public bairro?: string | null,
    public numero?: string | null,
    public cidade?: string | null,
    public uf?: string | null,
    public consultor?: IUser | null,
    public supervisor?: IUser | null,
    public filial?: IFilial | null
  ) {}
}

export function getClienteIdentifier(cliente: ICliente): number | undefined {
  return cliente.id;
}
