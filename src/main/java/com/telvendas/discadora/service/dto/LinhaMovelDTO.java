package com.telvendas.discadora.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.telvendas.discadora.domain.LinhaMovel} entity.
 */
public class LinhaMovelDTO implements Serializable {

    private Long id;

    private String numeroLinha;

    private String status;

    private LocalDate dataAtivacao;

    private String plano;

    private String operadora;

    private ClienteDTO cliente;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroLinha() {
        return numeroLinha;
    }

    public void setNumeroLinha(String numeroLinha) {
        this.numeroLinha = numeroLinha;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDataAtivacao() {
        return dataAtivacao;
    }

    public void setDataAtivacao(LocalDate dataAtivacao) {
        this.dataAtivacao = dataAtivacao;
    }

    public String getPlano() {
        return plano;
    }

    public void setPlano(String plano) {
        this.plano = plano;
    }

    public String getOperadora() {
        return operadora;
    }

    public void setOperadora(String operadora) {
        this.operadora = operadora;
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
        if (!(o instanceof LinhaMovelDTO)) {
            return false;
        }

        LinhaMovelDTO linhaMovelDTO = (LinhaMovelDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, linhaMovelDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LinhaMovelDTO{" +
            "id=" + getId() +
            ", numeroLinha='" + getNumeroLinha() + "'" +
            ", status='" + getStatus() + "'" +
            ", dataAtivacao='" + getDataAtivacao() + "'" +
            ", plano='" + getPlano() + "'" +
            ", operadora='" + getOperadora() + "'" +
            ", cliente=" + getCliente() +
            "}";
    }
}
