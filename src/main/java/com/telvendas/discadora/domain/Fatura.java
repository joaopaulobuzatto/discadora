package com.telvendas.discadora.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Fatura.
 */
@Entity
@Table(name = "fatura")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Fatura implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "numero_fatura")
    private String numeroFatura;

    @Column(name = "data_vencimento")
    private LocalDate dataVencimento;

    @Column(name = "valor_faturado", precision = 21, scale = 2)
    private BigDecimal valorFaturado;

    @Column(name = "valor_aberto", precision = 21, scale = 2)
    private BigDecimal valorAberto;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JsonIgnoreProperties(value = { "consultor", "supervisor", "filial" }, allowSetters = true)
    private Cliente cliente;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Fatura id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroFatura() {
        return this.numeroFatura;
    }

    public Fatura numeroFatura(String numeroFatura) {
        this.setNumeroFatura(numeroFatura);
        return this;
    }

    public void setNumeroFatura(String numeroFatura) {
        this.numeroFatura = numeroFatura;
    }

    public LocalDate getDataVencimento() {
        return this.dataVencimento;
    }

    public Fatura dataVencimento(LocalDate dataVencimento) {
        this.setDataVencimento(dataVencimento);
        return this;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public BigDecimal getValorFaturado() {
        return this.valorFaturado;
    }

    public Fatura valorFaturado(BigDecimal valorFaturado) {
        this.setValorFaturado(valorFaturado);
        return this;
    }

    public void setValorFaturado(BigDecimal valorFaturado) {
        this.valorFaturado = valorFaturado;
    }

    public BigDecimal getValorAberto() {
        return this.valorAberto;
    }

    public Fatura valorAberto(BigDecimal valorAberto) {
        this.setValorAberto(valorAberto);
        return this;
    }

    public void setValorAberto(BigDecimal valorAberto) {
        this.valorAberto = valorAberto;
    }

    public String getStatus() {
        return this.status;
    }

    public Fatura status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Cliente getCliente() {
        return this.cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Fatura cliente(Cliente cliente) {
        this.setCliente(cliente);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Fatura)) {
            return false;
        }
        return id != null && id.equals(((Fatura) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Fatura{" +
            "id=" + getId() +
            ", numeroFatura='" + getNumeroFatura() + "'" +
            ", dataVencimento='" + getDataVencimento() + "'" +
            ", valorFaturado=" + getValorFaturado() +
            ", valorAberto=" + getValorAberto() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
