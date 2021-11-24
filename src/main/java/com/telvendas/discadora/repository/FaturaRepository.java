package com.telvendas.discadora.repository;

import com.telvendas.discadora.domain.Fatura;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Fatura entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FaturaRepository extends JpaRepository<Fatura, Long> {}
