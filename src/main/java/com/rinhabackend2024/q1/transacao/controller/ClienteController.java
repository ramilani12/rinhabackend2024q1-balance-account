package com.rinhabackend2024.q1.transacao.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rinhabackend2024.q1.transacao.domain.Cliente;
import com.rinhabackend2024.q1.transacao.domain.Transacao;
import com.rinhabackend2024.q1.transacao.exception.ClienteNaoEncontradoException;
import com.rinhabackend2024.q1.transacao.exception.EstorouLimiteException;
import com.rinhabackend2024.q1.transacao.record.ClienteDto;
import com.rinhabackend2024.q1.transacao.record.ExtratoResponseDto;
import com.rinhabackend2024.q1.transacao.record.ExtratoResponseDto.TransacaoDetalheDto;
import com.rinhabackend2024.q1.transacao.record.TransacaoRequestDto;
import com.rinhabackend2024.q1.transacao.record.TransacaoResponseDto;
import com.rinhabackend2024.q1.transacao.repository.ClienteRepository;
import com.rinhabackend2024.q1.transacao.repository.TransacaoRepository;
import com.rinhabackend2024.q1.transacao.validador.ValidadorDescricaoTransacao;
import com.rinhabackend2024.q1.transacao.validador.ValidadorTipoTransacao;
import com.rinhabackend2024.q1.transacao.validador.ValidadorTransacao;
import com.rinhabackend2024.q1.transacao.validador.ValidadorValorTransacao;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("clientes")
@Slf4j
public class ClienteController {

	private final ClienteRepository clienteRepository;

	private final TransacaoRepository transacaoRepository;

	private final List<ValidadorTransacao> validadores = Arrays.asList(new ValidadorDescricaoTransacao(),
			new ValidadorValorTransacao(), new ValidadorTipoTransacao());

	/**
	 * @param clienteRepository
	 * @param transacaoRepository
	 */
	public ClienteController(final ClienteRepository clienteRepository, final TransacaoRepository transacaoRepository) {
		this.clienteRepository = clienteRepository;
		this.transacaoRepository = transacaoRepository;
	}

	@GetMapping
	public ResponseEntity<String> index() {
		return ResponseEntity.ok("Ok");
	}

	@PostMapping
	public ResponseEntity<String> salvarCliente(@RequestBody final ClienteDto clienteDto) {

		var cliente = new Cliente();
		cliente.setLimite(clienteDto.limite());
		cliente.setSaldo(clienteDto.saldo());
		this.clienteRepository.save(cliente);

		return ResponseEntity.status(201).body("Ok");

	}

	@GetMapping("/{id}")
	public ResponseEntity<Cliente> buscarPorId(@PathVariable("id") final Long idCliente) {

		var cliente = clienteRepository.findById(idCliente).orElseThrow(ClienteNaoEncontradoException::new);

		return ResponseEntity.ok(cliente);

	}

	@Transactional(isolation = Isolation.READ_COMMITTED)
	@PostMapping("/{id}/transacoes")
	public ResponseEntity<TransacaoResponseDto> realizarTransacao(@PathVariable("id") Long idCliente,
			@RequestBody final TransacaoRequestDto dto) {

		validadores.forEach(validador -> validador.validaTransacao(dto));

		var cliente = buscarCliente(idCliente);

		Long valor = Long.valueOf(dto.valor());

		if ("c".equalsIgnoreCase(dto.tipo())) {

			long saldoNovo = cliente.getSaldo() + valor;

			return ResponseEntity.ok(criarTransacao(cliente, dto, saldoNovo));

		} else {

			var limite = cliente.getLimite();
			var saldo = cliente.getSaldo();

			if (valor <= saldo + limite) {
				saldo -= valor;

			} else {

				throw new EstorouLimiteException();

			}

			return ResponseEntity.ok(criarTransacao(cliente, dto, saldo));

		}

	}

	@Transactional(isolation = Isolation.READ_COMMITTED)
	@GetMapping("/{id}/extrato")
	public ResponseEntity<ExtratoResponseDto> buscarExtratoCliente(@PathVariable("id") final Long idCliente) {

		var cliente = buscarCliente(idCliente);

		var ultimasTransacoesDb = transacaoRepository.findFirst10ByCliente_IdOrderByRealizadaEmDesc(idCliente);

		List<TransacaoDetalheDto> transacoes = ultimasTransacoesDb.stream()
				.map(transacao -> new TransacaoDetalheDto(transacao.getValor(), transacao.getTipoTransacao(),
						transacao.getDescricao(), transacao.getRealizadaEm()))
				.collect(Collectors.toCollection(ArrayList::new));

		var saldo = new ExtratoResponseDto.SaldoDto(cliente.getSaldo(), LocalDateTime.now(), cliente.getLimite());

		return ResponseEntity.ok(new ExtratoResponseDto(saldo, transacoes));
	}

	/**
	 * Busca o Cliente e valida se o Id está entre 1 a 5
	 * 
	 * @param idCliente
	 * @return
	 */
	private Cliente buscarCliente(final Long idCliente) {

		if (idCliente == null) {
			throw new ClienteNaoEncontradoException();
		}

		if (idCliente < 1 || idCliente > 5) {
			throw new ClienteNaoEncontradoException();
		}

		var cliente = clienteRepository.findById(idCliente).orElseThrow(ClienteNaoEncontradoException::new);
		return cliente;
	}

	/**
	 * Cria a Transacao e Salva o novo Saldo do Cliente
	 * 
	 * @param cliente
	 * @param dto
	 * @param novoSaldo
	 * @return
	 */
	private TransacaoResponseDto criarTransacao(final Cliente cliente, final TransacaoRequestDto dto,
			final Long novoSaldo) {

		var transacao = new Transacao();
		transacao.setCliente(cliente);
		transacao.setValor(Long.valueOf(dto.valor()));
		transacao.setTipoTransacao(dto.tipo());
		transacao.setDescricao(dto.descricao());
		transacao.setRealizadaEm(LocalDateTime.now());

		//transacaoRepository.save(transacao);

		cliente.addTransacao(transacao);
		
		cliente.setSaldo(novoSaldo);

		clienteRepository.save(cliente);

		return new TransacaoResponseDto(cliente.getLimite(), cliente.getSaldo());

	}

}
