package com.rinhabackend2024.q1.transacao.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import com.rinhabackend2024.q1.transacao.domain.Cliente;

import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("select c from Cliente c where c.id = :id")
	@QueryHints({ @QueryHint(name = "jakarta.persistence.lock.timeout", value = "4000") })
	Optional<Cliente> findById(final Long id);

}
