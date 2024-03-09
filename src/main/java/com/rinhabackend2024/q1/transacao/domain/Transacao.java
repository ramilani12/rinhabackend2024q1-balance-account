package com.rinhabackend2024.q1.transacao.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Table(name = "transacao")
@Entity
public class Transacao implements Serializable {
	private static final long serialVersionUID = 6141097659053821567L;

	@Id
	@SequenceGenerator(name = "transacao_seq", sequenceName = "transacao_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transacao_seq")
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "valor", nullable = false)
	private Long valor;

	@Column(name = "tipo_transacao", nullable = false)
	private String tipoTransacao;

	@Column(name = "descricao", nullable = false, length = 10)
	private String descricao;

	@Column(name = "realizada_em", nullable = false)
	private LocalDateTime realizadaEm;

	@ManyToOne(optional = false)
	@JoinColumn(name = "id_cliente", nullable = false)
	private Cliente cliente;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getValor() {
		return valor;
	}

	public void setValor(Long valor) {
		this.valor = valor;
	}

	public String getTipoTransacao() {
		return tipoTransacao;
	}

	public void setTipoTransacao(String tipoTransacao) {
		this.tipoTransacao = tipoTransacao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public LocalDateTime getRealizadaEm() {
		return realizadaEm;
	}

	public void setRealizadaEm(LocalDateTime realizadaEm) {
		this.realizadaEm = realizadaEm;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

}
