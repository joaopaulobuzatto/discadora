package com.telvendas.discadora.repository;

import com.telvendas.discadora.domain.Filial;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Filial entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FilialRepository extends JpaRepository<Filial, Long> {}
