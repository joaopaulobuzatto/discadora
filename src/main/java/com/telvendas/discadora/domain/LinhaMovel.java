package com.telvendas.discadora.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A LinhaMovel.
 */
@Entity
@Table(name = "linha_movel")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class LinhaMovel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "numero_linha")
    private String numeroLinha;

    @Column(name = "status")
    private String status;

    @Column(name = "data_ativacao")
    private LocalDate dataAtivacao;

    @Column(name = "plano")
    private String plano;

    @Column(name = "operadora")
    private String operadora;

    @ManyToOne
    @JsonIgnoreProperties(value = { "consultor", "supervisor", "filial" }, allowSetters = true)
    private Cliente cliente;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public LinhaMovel id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroLinha() {
        return this.numeroLinha;
    }

    public LinhaMovel numeroLinha(String numeroLinha) {
        this.setNumeroLinha(numeroLinha);
        return this;
    }

    public void setNumeroLinha(String numeroLinha) {
        this.numeroLinha = numeroLinha;
    }

    public String getStatus() {
        return this.status;
    }

    public LinhaMovel status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDataAtivacao() {
        return this.dataAtivacao;
    }

    public LinhaMovel dataAtivacao(LocalDate dataAtivacao) {
        this.setDataAtivacao(dataAtivacao);
        return this;
    }

    public void setDataAtivacao(LocalDate dataAtivacao) {
        this.dataAtivacao = dataAtivacao;
    }

    public String getPlano() {
        return this.plano;
    }

    public LinhaMovel plano(String plano) {
        this.setPlano(plano);
        return this;
    }

    public void setPlano(String plano) {
        this.plano = plano;
    }

    public String getOperadora() {
        return this.operadora;
    }

    public LinhaMovel operadora(String operadora) {
        this.setOperadora(operadora);
        return this;
    }

    public void setOperadora(String operadora) {
        this.operadora = operadora;
    }

    public Cliente getCliente() {
        return this.cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public LinhaMovel cliente(Cliente cliente) {
        this.setCliente(cliente);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LinhaMovel)) {
            return false;
        }
        return id != null && id.equals(((LinhaMovel) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LinhaMovel{" +
            "id=" + getId() +
            ", numeroLinha='" + getNumeroLinha() + "'" +
            ", status='" + getStatus() + "'" +
            ", dataAtivacao='" + getDataAtivacao() + "'" +
            ", plano='" + getPlano() + "'" +
            ", operadora='" + getOperadora() + "'" +
            "}";
    }
}
