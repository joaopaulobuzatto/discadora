package com.telvendas.discadora.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.telvendas.discadora.domain.ResponsavelCliente} entity.
 */
public class ResponsavelClienteDTO implements Serializable {

    private Long id;

    private String cpf;

    private String nome;

    private LocalDate dataNascimento;

    private String rg;

    private String telefone1;

    private String telefone2;

    private String email;

    private String nomeMae;

    private String nomePai;

    private String funcao;

    private ClienteDTO cliente;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getTelefone1() {
        return telefone1;
    }

    public void setTelefone1(String telefone1) {
        this.telefone1 = telefone1;
    }

    public String getTelefone2() {
        return telefone2;
    }

    public void setTelefone2(String telefone2) {
        this.telefone2 = telefone2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNomeMae() {
        return nomeMae;
    }

    public void setNomeMae(String nomeMae) {
        this.nomeMae = nomeMae;
    }

    public String getNomePai() {
        return nomePai;
    }

    public void setNomePai(String nomePai) {
        this.nomePai = nomePai;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public ClienteDTO getCliente() {
        return cliente;
    }

    public void setCliente(ClienteDTO cliente) {
        this.cliente = cliente;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ResponsavelClienteDTO)) {
            return false;
        }

        ResponsavelClienteDTO responsavelClienteDTO = (ResponsavelClienteDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, responsavelClienteDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ResponsavelClienteDTO{" +
            "id=" + getId() +
            ", cpf='" + getCpf() + "'" +
            ", nome='" + getNome() + "'" +
            ", dataNascimento='" + getDataNascimento() + "'" +
            ", rg='" + getRg() + "'" +
            ", telefone1='" + getTelefone1() + "'" +
            ", telefone2='" + getTelefone2() + "'" +
            ", email='" + getEmail() + "'" +
            ", nomeMae='" + getNomeMae() + "'" +
            ", nomePai='" + getNomePai() + "'" +
            ", funcao='" + getFuncao() + "'" +
            ", cliente=" + getCliente() +
            "}";
    }
}
