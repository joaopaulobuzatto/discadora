package com.telvendas.discadora.repository;

import com.telvendas.discadora.domain.Cliente;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Cliente entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    @Query("select cliente from Cliente cliente where cliente.consultor.login = ?#{principal.username}")
    List<Cliente> findByConsultorIsCurrentUser();
}
