package com.rinhabackend2024.q1.transacao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rinhabackend2024.q1.transacao.domain.Transacao;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
	List<Transacao> findFirst10ByCliente_IdOrderByRealizadaEmDesc(Long id);

}
