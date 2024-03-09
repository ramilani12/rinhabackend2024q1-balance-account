package com.rinhabackend2024.q1.transacao.validador;

import org.springframework.util.StringUtils;

import com.rinhabackend2024.q1.transacao.exception.CampoInvalidoTransacaoException;
import com.rinhabackend2024.q1.transacao.record.TransacaoRequestDto;

public class ValidadorTipoTransacao implements ValidadorTransacao {

	@Override
	public void validaTransacao(TransacaoRequestDto request) {
		
		
		if (StringUtils.isEmpty(request.tipo())) {
			throw new CampoInvalidoTransacaoException();
		}
		
		if (!"c".equalsIgnoreCase(request.tipo()) && !"d".equalsIgnoreCase(request.tipo())) {
			throw new CampoInvalidoTransacaoException();
		}

	}

}
