package com.rinhabackend2024.q1.transacao.record;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public record ExtratoResponseDto(SaldoDto saldo, List<TransacaoDetalheDto> ultimas_transacoes) implements Serializable {

	public record TransacaoDetalheDto(Long valor, String tipo, String descricao, LocalDateTime realizada_em)
			implements Serializable {
	}

	public record SaldoDto(Long total, LocalDateTime data_extrato, Long limite) implements Serializable {
	}
}
