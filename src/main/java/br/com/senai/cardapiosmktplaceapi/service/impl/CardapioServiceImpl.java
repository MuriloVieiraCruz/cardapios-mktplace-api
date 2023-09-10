package br.com.senai.cardapiosmktplaceapi.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.senai.cardapiosmktplaceapi.entity.Cardapio;
import br.com.senai.cardapiosmktplaceapi.entity.Restaurante;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Status;
import br.com.senai.cardapiosmktplaceapi.service.CardapioService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CardapioServiceImpl implements CardapioService{

	@Override
	public Cardapio salvar(Cardapio cardapio) {
		return null;
	}

	@Override
	public void atualizar(String nome, String descricao, Status status) {
		
	}

	@Override
	public Page<Cardapio> listarPor(Restaurante restaurante, Pageable paginacao) {
		return null;
	}

	@Override
	public Cardapio buscarPor(Integer id) {
		return null;
	}

	@Override
	public void atualizarStatusPor(Integer id, Status status) {
		
	}

}
