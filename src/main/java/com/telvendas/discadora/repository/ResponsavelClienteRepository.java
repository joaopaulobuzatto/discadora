package com.telvendas.discadora.repository;

import com.telvendas.discadora.domain.ResponsavelCliente;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ResponsavelCliente entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResponsavelClienteRepository extends JpaRepository<ResponsavelCliente, Long> {}
