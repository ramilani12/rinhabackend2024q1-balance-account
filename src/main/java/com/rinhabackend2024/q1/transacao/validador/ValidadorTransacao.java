package com.rinhabackend2024.q1.transacao.validador;

import com.rinhabackend2024.q1.transacao.record.TransacaoRequestDto;

public interface ValidadorTransacao {
	
	
	void validaTransacao(final TransacaoRequestDto request);

}
