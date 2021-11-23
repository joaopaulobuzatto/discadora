package com.telvendas.discadora.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Cliente.
 */
@Entity
@Table(name = "cliente")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "cnpj")
    private String cnpj;

    @Column(name = "razao_social")
    private String razaoSocial;

    @Column(name = "nome_fantasia")
    private String nomeFantasia;

    @Column(name = "telefone_1")
    private String telefone1;

    @Column(name = "telefone_2")
    private String telefone2;

    @Column(name = "telefone_3")
    private String telefone3;

    @Column(name = "telefone_4")
    private String telefone4;

    @Column(name = "telefone_5")
    private String telefone5;

    @Column(name = "telefone_6")
    private String telefone6;

    @Column(name = "telefone_7")
    private String telefone7;

    @Column(name = "telefone_8")
    private String telefone8;

    @Column(name = "email_1")
    private String email1;

    @Column(name = "email_2")
    private String email2;

    @Column(name = "email_3")
    private String email3;

    @Column(name = "email_4")
    private String email4;

    @Column(name = "email_5")
    private String email5;

    @Column(name = "email_6")
    private String email6;

    @Column(name = "quantidade_funcionarios")
    private Integer quantidadeFuncionarios;

    @Column(name = "quantidade_linhas_movel")
    private Integer quantidadeLinhasMovel;

    @Column(name = "quantidade_linhas_fixa")
    private Integer quantidadeLinhasFixa;

    @Column(name = "nome_contato_principal")
    private String nomeContatoPrincipal;

    @Column(name = "telefone_1_contato_principal")
    private String telefone1ContatoPrincipal;

    @Column(name = "telefone_2_contato_principal")
    private String telefone2ContatoPrincipal;

    @Column(name = "email_contato_principal")
    private String emailContatoPrincipal;

    @Column(name = "observacao")
    private String observacao;

    @Column(name = "cep")
    private String cep;

    @Column(name = "logradouro")
    private String logradouro;

    @Column(name = "bairro")
    private String bairro;

    @Column(name = "numero")
    private String numero;

    @Column(name = "cidade")
    private String cidade;

    @Column(name = "uf")
    private String uf;

    @ManyToOne
    private User consultor;

    @ManyToOne
    private User supervisor;

    @ManyToOne
    private Filial filial;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Cliente id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCnpj() {
        return this.cnpj;
    }

    public Cliente cnpj(String cnpj) {
        this.setCnpj(cnpj);
        return this;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getRazaoSocial() {
        return this.razaoSocial;
    }

    public Cliente razaoSocial(String razaoSocial) {
        this.setRazaoSocial(razaoSocial);
        return this;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getNomeFantasia() {
        return this.nomeFantasia;
    }

    public Cliente nomeFantasia(String nomeFantasia) {
        this.setNomeFantasia(nomeFantasia);
        return this;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getTelefone1() {
        return this.telefone1;
    }

    public Cliente telefone1(String telefone1) {
        this.setTelefone1(telefone1);
        return this;
    }

    public void setTelefone1(String telefone1) {
        this.telefone1 = telefone1;
    }

    public String getTelefone2() {
        return this.telefone2;
    }

    public Cliente telefone2(String telefone2) {
        this.setTelefone2(telefone2);
        return this;
    }

    public void setTelefone2(String telefone2) {
        this.telefone2 = telefone2;
    }

    public String getTelefone3() {
        return this.telefone3;
    }

    public Cliente telefone3(String telefone3) {
        this.setTelefone3(telefone3);
        return this;
    }

    public void setTelefone3(String telefone3) {
        this.telefone3 = telefone3;
    }

    public String getTelefone4() {
        return this.telefone4;
    }

    public Cliente telefone4(String telefone4) {
        this.setTelefone4(telefone4);
        return this;
    }

    public void setTelefone4(String telefone4) {
        this.telefone4 = telefone4;
    }

    public String getTelefone5() {
        return this.telefone5;
    }

    public Cliente telefone5(String telefone5) {
        this.setTelefone5(telefone5);
        return this;
    }

    public void setTelefone5(String telefone5) {
        this.telefone5 = telefone5;
    }

    public String getTelefone6() {
        return this.telefone6;
    }

    public Cliente telefone6(String telefone6) {
        this.setTelefone6(telefone6);
        return this;
    }

    public void setTelefone6(String telefone6) {
        this.telefone6 = telefone6;
    }

    public String getTelefone7() {
        return this.telefone7;
    }

    public Cliente telefone7(String telefone7) {
        this.setTelefone7(telefone7);
        return this;
    }

    public void setTelefone7(String telefone7) {
        this.telefone7 = telefone7;
    }

    public String getTelefone8() {
        return this.telefone8;
    }

    public Cliente telefone8(String telefone8) {
        this.setTelefone8(telefone8);
        return this;
    }

    public void setTelefone8(String telefone8) {
        this.telefone8 = telefone8;
    }

    public String getEmail1() {
        return this.email1;
    }

    public Cliente email1(String email1) {
        this.setEmail1(email1);
        return this;
    }

    public void setEmail1(String email1) {
        this.email1 = email1;
    }

    public String getEmail2() {
        return this.email2;
    }

    public Cliente email2(String email2) {
        this.setEmail2(email2);
        return this;
    }

    public void setEmail2(String email2) {
        this.email2 = email2;
    }

    public String getEmail3() {
        return this.email3;
    }

    public Cliente email3(String email3) {
        this.setEmail3(email3);
        return this;
    }

    public void setEmail3(String email3) {
        this.email3 = email3;
    }

    public String getEmail4() {
        return this.email4;
    }

    public Cliente email4(String email4) {
        this.setEmail4(email4);
        return this;
    }

    public void setEmail4(String email4) {
        this.email4 = email4;
    }

    public String getEmail5() {
        return this.email5;
    }

    public Cliente email5(String email5) {
        this.setEmail5(email5);
        return this;
    }

    public void setEmail5(String email5) {
        this.email5 = email5;
    }

    public String getEmail6() {
        return this.email6;
    }

    public Cliente email6(String email6) {
        this.setEmail6(email6);
        return this;
    }

    public void setEmail6(String email6) {
        this.email6 = email6;
    }

    public Integer getQuantidadeFuncionarios() {
        return this.quantidadeFuncionarios;
    }

    public Cliente quantidadeFuncionarios(Integer quantidadeFuncionarios) {
        this.setQuantidadeFuncionarios(quantidadeFuncionarios);
        return this;
    }

    public void setQuantidadeFuncionarios(Integer quantidadeFuncionarios) {
        this.quantidadeFuncionarios = quantidadeFuncionarios;
    }

    public Integer getQuantidadeLinhasMovel() {
        return this.quantidadeLinhasMovel;
    }

    public Cliente quantidadeLinhasMovel(Integer quantidadeLinhasMovel) {
        this.setQuantidadeLinhasMovel(quantidadeLinhasMovel);
        return this;
    }

    public void setQuantidadeLinhasMovel(Integer quantidadeLinhasMovel) {
        this.quantidadeLinhasMovel = quantidadeLinhasMovel;
    }

    public Integer getQuantidadeLinhasFixa() {
        return this.quantidadeLinhasFixa;
    }

    public Cliente quantidadeLinhasFixa(Integer quantidadeLinhasFixa) {
        this.setQuantidadeLinhasFixa(quantidadeLinhasFixa);
        return this;
    }

    public void setQuantidadeLinhasFixa(Integer quantidadeLinhasFixa) {
        this.quantidadeLinhasFixa = quantidadeLinhasFixa;
    }

    public String getNomeContatoPrincipal() {
        return this.nomeContatoPrincipal;
    }

    public Cliente nomeContatoPrincipal(String nomeContatoPrincipal) {
        this.setNomeContatoPrincipal(nomeContatoPrincipal);
        return this;
    }

    public void setNomeContatoPrincipal(String nomeContatoPrincipal) {
        this.nomeContatoPrincipal = nomeContatoPrincipal;
    }

    public String getTelefone1ContatoPrincipal() {
        return this.telefone1ContatoPrincipal;
    }

    public Cliente telefone1ContatoPrincipal(String telefone1ContatoPrincipal) {
        this.setTelefone1ContatoPrincipal(telefone1ContatoPrincipal);
        return this;
    }

    public void setTelefone1ContatoPrincipal(String telefone1ContatoPrincipal) {
        this.telefone1ContatoPrincipal = telefone1ContatoPrincipal;
    }

    public String getTelefone2ContatoPrincipal() {
        return this.telefone2ContatoPrincipal;
    }

    public Cliente telefone2ContatoPrincipal(String telefone2ContatoPrincipal) {
        this.setTelefone2ContatoPrincipal(telefone2ContatoPrincipal);
        return this;
    }

    public void setTelefone2ContatoPrincipal(String telefone2ContatoPrincipal) {
        this.telefone2ContatoPrincipal = telefone2ContatoPrincipal;
    }

    public String getEmailContatoPrincipal() {
        return this.emailContatoPrincipal;
    }

    public Cliente emailContatoPrincipal(String emailContatoPrincipal) {
        this.setEmailContatoPrincipal(emailContatoPrincipal);
        return this;
    }

    public void setEmailContatoPrincipal(String emailContatoPrincipal) {
        this.emailContatoPrincipal = emailContatoPrincipal;
    }

    public String getObservacao() {
        return this.observacao;
    }

    public Cliente observacao(String observacao) {
        this.setObservacao(observacao);
        return this;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getCep() {
        return this.cep;
    }

    public Cliente cep(String cep) {
        this.setCep(cep);
        return this;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return this.logradouro;
    }

    public Cliente logradouro(String logradouro) {
        this.setLogradouro(logradouro);
        return this;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getBairro() {
        return this.bairro;
    }

    public Cliente bairro(String bairro) {
        this.setBairro(bairro);
        return this;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getNumero() {
        return this.numero;
    }

    public Cliente numero(String numero) {
        this.setNumero(numero);
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCidade() {
        return this.cidade;
    }

    public Cliente cidade(String cidade) {
        this.setCidade(cidade);
        return this;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return this.uf;
    }

    public Cliente uf(String uf) {
        this.setUf(uf);
        return this;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public User getConsultor() {
        return this.consultor;
    }

    public void setConsultor(User user) {
        this.consultor = user;
    }

    public Cliente consultor(User user) {
        this.setConsultor(user);
        return this;
    }

    public User getSupervisor() {
        return this.supervisor;
    }

    public void setSupervisor(User user) {
        this.supervisor = user;
    }

    public Cliente supervisor(User user) {
        this.setSupervisor(user);
        return this;
    }

    public Filial getFilial() {
        return this.filial;
    }

    public void setFilial(Filial filial) {
        this.filial = filial;
    }

    public Cliente filial(Filial filial) {
        this.setFilial(filial);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cliente)) {
            return false;
        }
        return id != null && id.equals(((Cliente) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Cliente{" +
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
            "}";
    }
}
