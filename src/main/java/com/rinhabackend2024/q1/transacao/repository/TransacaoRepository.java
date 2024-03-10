package com.rinhabackend2024.q1.transacao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rinhabackend2024.q1.transacao.domain.Transacao;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

	@Query(value = "SELECT * FROM transacao t WHERE t.id_cliente = :id ORDER BY t.realizada_em DESC LIMIT 10" , nativeQuery = true)
	List<Transacao> findFirst10ByCliente_IdOrderByRealizadaEmDesc(@Param("id") final Long id);

}
