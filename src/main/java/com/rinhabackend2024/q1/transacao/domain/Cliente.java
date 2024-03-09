package com.rinhabackend2024.q1.transacao.domain;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Entity
@Table(name = "cliente")
public class Cliente implements Serializable{
    private static final long serialVersionUID = 4091816356079930632L;

	@Id
	@SequenceGenerator(name="cliente_seq",sequenceName="cliente_seq" , allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "cliente_seq")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "limite", nullable = false)
    private Long limite;

    @Column(name = "saldo")
    private Long saldo;
    
    public Cliente() {
    	
    }

    public Cliente(Long id, Long limite, Long saldo) {
        this.id = id;
        this.limite = limite;
        this.saldo = saldo;
    }

    @OneToMany(mappedBy = "cliente", orphanRemoval = true)
    private List<Transacao> transacoes = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getLimite() {
		return limite;
	}

	public void setLimite(Long limite) {
		this.limite = limite;
	}

	public Long getSaldo() {
		return saldo;
	}

	public void setSaldo(Long saldo) {
		this.saldo = saldo;
	}
    
    
    
    

}
