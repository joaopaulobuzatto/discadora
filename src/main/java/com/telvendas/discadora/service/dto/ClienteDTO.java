package com.telvendas.discadora.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.telvendas.discadora.domain.Cliente} entity.
 */
public class ClienteDTO implements Serializable {

    private Long id;

    private String cnpj;

    private String razaoSocial;

    private String nomeFantasia;

    private String telefone1;

    private String telefone2;

    private String telefone3;

    private String telefone4;

    private String telefone5;

    private String telefone6;

    private String telefone7;

    private String telefone8;

    private String email1;

    private String email2;

    private String email3;

    private String email4;

    private String email5;

    private String email6;

    private Integer quantidadeFuncionarios;

    private Integer quantidadeLinhasMovel;

    private Integer quantidadeLinhasFixa;

    private String nomeContatoPrincipal;

    private String telefone1ContatoPrincipal;

    private String telefone2ContatoPrincipal;

    private String emailContatoPrincipal;

    private String observacao;

    private String cep;

    private String logradouro;

    private String bairro;

    private String numero;

    private String cidade;

    private String uf;

    private String createdBy;

    private Instant createdDate;

    private String lastModifiedBy;

    private Instant lastModifiedDate;

    private UserDTO consultor;

    private FilialDTO filial;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
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

    public String getTelefone3() {
        return telefone3;
    }

    public void setTelefone3(String telefone3) {
        this.telefone3 = telefone3;
    }

    public String getTelefone4() {
        return telefone4;
    }

    public void setTelefone4(String telefone4) {
        this.telefone4 = telefone4;
    }

    public String getTelefone5() {
        return telefone5;
    }

    public void setTelefone5(String telefone5) {
        this.telefone5 = telefone5;
    }

    public String getTelefone6() {
        return telefone6;
    }

    public void setTelefone6(String telefone6) {
        this.telefone6 = telefone6;
    }

    public String getTelefone7() {
        return telefone7;
    }

    public void setTelefone7(String telefone7) {
        this.telefone7 = telefone7;
    }

    public String getTelefone8() {
        return telefone8;
    }

    public void setTelefone8(String telefone8) {
        this.telefone8 = telefone8;
    }

    public String getEmail1() {
        return email1;
    }

    public void setEmail1(String email1) {
        this.email1 = email1;
    }

    public String getEmail2() {
        return email2;
    }

    public void setEmail2(String email2) {
        this.email2 = email2;
    }

    public String getEmail3() {
        return email3;
    }

    public void setEmail3(String email3) {
        this.email3 = email3;
    }

    public String getEmail4() {
        return email4;
    }

    public void setEmail4(String email4) {
        this.email4 = email4;
    }

    public String getEmail5() {
        return email5;
    }

    public void setEmail5(String email5) {
        this.email5 = email5;
    }

    public String getEmail6() {
        return email6;
    }

    public void setEmail6(String email6) {
        this.email6 = email6;
    }

    public Integer getQuantidadeFuncionarios() {
        return quantidadeFuncionarios;
    }

    public void setQuantidadeFuncionarios(Integer quantidadeFuncionarios) {
        this.quantidadeFuncionarios = quantidadeFuncionarios;
    }

    public Integer getQuantidadeLinhasMovel() {
        return quantidadeLinhasMovel;
    }

    public void setQuantidadeLinhasMovel(Integer quantidadeLinhasMovel) {
        this.quantidadeLinhasMovel = quantidadeLinhasMovel;
    }

    public Integer getQuantidadeLinhasFixa() {
        return quantidadeLinhasFixa;
    }

    public void setQuantidadeLinhasFixa(Integer quantidadeLinhasFixa) {
        this.quantidadeLinhasFixa = quantidadeLinhasFixa;
    }

    public String getNomeContatoPrincipal() {
        return nomeContatoPrincipal;
    }

    public void setNomeContatoPrincipal(String nomeContatoPrincipal) {
        this.nomeContatoPrincipal = nomeContatoPrincipal;
    }

    public String getTelefone1ContatoPrincipal() {
        return telefone1ContatoPrincipal;
    }

    public void setTelefone1ContatoPrincipal(String telefone1ContatoPrincipal) {
        this.telefone1ContatoPrincipal = telefone1ContatoPrincipal;
    }

    public String getTelefone2ContatoPrincipal() {
        return telefone2ContatoPrincipal;
    }

    public void setTelefone2ContatoPrincipal(String telefone2ContatoPrincipal) {
        this.telefone2ContatoPrincipal = telefone2ContatoPrincipal;
    }

    public String getEmailContatoPrincipal() {
        return emailContatoPrincipal;
    }

    public void setEmailContatoPrincipal(String emailContatoPrincipal) {
        this.emailContatoPrincipal = emailContatoPrincipal;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public UserDTO getConsultor() {
        return consultor;
    }

    public void setConsultor(UserDTO consultor) {
        this.consultor = consultor;
    }

    public FilialDTO getFilial() {
        return filial;
    }

    public void setFilial(FilialDTO filial) {
        this.filial = filial;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClienteDTO)) {
            return false;
        }

        ClienteDTO clienteDTO = (ClienteDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, clienteDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClienteDTO{" +
            "id=" + getId() +
            ", cnpj='" + getCnpj() + "'" +
            ", razaoSocial='" + getRazaoSocial() + "'" +
            ", nomeFantasia='" + getNomeFantasia() + "'" +
            ", telefone1='" + getTelefone1() + "'" +
            ", telefone2='" + getTelefone2() + "'" +
            ", telefone3='" + getTelefone3() + "'" +
            ", telefone4='" + getTelefone4() + "'" +
            ", telefone5='" + getTelefone5() + "'" +
            ", telefone6='" + getTelefone6() + "'" +
            ", telefone7='" + getTelefone7() + "'" +
            ", telefone8='" + getTelefone8() + "'" +
            ", email1='" + getEmail1() + "'" +
            ", email2='" + getEmail2() + "'" +
            ", email3='" + getEmail3() + "'" +
            ", email4='" + getEmail4() + "'" +
            ", email5='" + getEmail5() + "'" +
            ", email6='" + getEmail6() + "'" +
            ", quantidadeFuncionarios=" + getQuantidadeFuncionarios() +
            ", quantidadeLinhasMovel=" + getQuantidadeLinhasMovel() +
            ", quantidadeLinhasFixa=" + getQuantidadeLinhasFixa() +
            ", nomeContatoPrincipal='" + getNomeContatoPrincipal() + "'" +
            ", telefone1ContatoPrincipal='" + getTelefone1ContatoPrincipal() + "'" +
            ", telefone2ContatoPrincipal='" + getTelefone2ContatoPrincipal() + "'" +
            ", emailContatoPrincipal='" + getEmailContatoPrincipal() + "'" +
            ", observacao='" + getObservacao() + "'" +
            ", cep='" + getCep() + "'" +
            ", logradouro='" + getLogradouro() + "'" +
            ", bairro='" + getBairro() + "'" +
            ", numero='" + getNumero() + "'" +
            ", cidade='" + getCidade() + "'" +
            ", uf='" + getUf() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", consultor=" + getConsultor() +
            ", filial=" + getFilial() +
            "}";
    }
}
