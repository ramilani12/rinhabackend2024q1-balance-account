package com.rinhabackend2024.q1.transacao.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rinhabackend2024.q1.transacao.domain.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	//@Lock(LockModeType.PESSIMISTIC_WRITE)

    @Query(value = "SELECT * FROM cliente WHERE id = :id FOR UPDATE", nativeQuery = true)
	//@QueryHints({ @QueryHint(name = "jakarta.persistence.lock.timeout", value = "4000") })
	Optional<Cliente> findById(@Param("id") final Long id);

}
