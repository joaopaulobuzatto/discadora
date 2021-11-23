package com.telvendas.discadora.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.telvendas.discadora.domain.Fatura} entity.
 */
public class FaturaDTO implements Serializable {

    private Long id;

    private String numeroFatura;

    private LocalDate dataVencimento;

    private BigDecimal valorFaturado;

    private BigDecimal valorAberto;

    private String status;

    private ClienteDTO cliente;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroFatura() {
        return numeroFatura;
    }

    public void setNumeroFatura(String numeroFatura) {
        this.numeroFatura = numeroFatura;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public BigDecimal getValorFaturado() {
        return valorFaturado;
    }

    public void setValorFaturado(BigDecimal valorFaturado) {
        this.valorFaturado = valorFaturado;
    }

    public BigDecimal getValorAberto() {
        return valorAberto;
    }

    public void setValorAberto(BigDecimal valorAberto) {
        this.valorAberto = valorAberto;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
        if (!(o instanceof FaturaDTO)) {
            return false;
        }

        FaturaDTO faturaDTO = (FaturaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, faturaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FaturaDTO{" +
            "id=" + getId() +
            ", numeroFatura='" + getNumeroFatura() + "'" +
            ", dataVencimento='" + getDataVencimento() + "'" +
            ", valorFaturado=" + getValorFaturado() +
            ", valorAberto=" + getValorAberto() +
            ", status='" + getStatus() + "'" +
            ", cliente=" + getCliente() +
            "}";
    }
}
