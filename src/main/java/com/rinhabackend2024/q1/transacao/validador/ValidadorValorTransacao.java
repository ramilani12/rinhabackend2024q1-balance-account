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
public class ValidadorValorTransacao implements ValidadorTransacao {

	@Override
	public void validaTransacao(TransacaoRequestDto request) {
		
		if (StringUtils.isEmpty(request.valor())) {
			throw new CampoInvalidoTransacaoException();
		}
		
		try {
			 var valor = Long.parseLong(request.valor());
			 
			 if (valor < 0) {
				 throw new CampoInvalidoTransacaoException();
			 }
			 
		} catch (NumberFormatException error) {
			throw new CampoInvalidoTransacaoException();
		}
		
	}

}
