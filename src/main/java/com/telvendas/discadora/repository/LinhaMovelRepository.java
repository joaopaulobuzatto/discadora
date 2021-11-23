package com.telvendas.discadora.repository;

import com.telvendas.discadora.domain.LinhaMovel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the LinhaMovel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LinhaMovelRepository extends JpaRepository<LinhaMovel, Long> {}
