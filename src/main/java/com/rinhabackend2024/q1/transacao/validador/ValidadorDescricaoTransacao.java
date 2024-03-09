/**
 * 
 */
package com.rinhabackend2024.q1.transacao.validador;

import org.springframework.util.StringUtils;

import com.rinhabackend2024.q1.transacao.exception.CampoInvalidoTransacaoException;
import com.rinhabackend2024.q1.transacao.record.TransacaoRequestDto;

/**
 * 
 */
public class ValidadorDescricaoTransacao implements ValidadorTransacao {

	@Override
	public void validaTransacao(TransacaoRequestDto request) {
		
		if (StringUtils.isEmpty(request.descricao())) {
			throw new CampoInvalidoTransacaoException();
		}
		
		if (request.descricao().trim().length() > 10) {
			throw new CampoInvalidoTransacaoException();
		}

	}

}
