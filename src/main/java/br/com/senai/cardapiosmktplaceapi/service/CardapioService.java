package br.com.senai.cardapiosmktplaceapi.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import br.com.senai.cardapiosmktplaceapi.dto.CardapioSalvo;
import br.com.senai.cardapiosmktplaceapi.dto.NovoCardapio;
import br.com.senai.cardapiosmktplaceapi.entity.Cardapio;
import br.com.senai.cardapiosmktplaceapi.entity.Restaurante;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Status;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Validated
public interface CardapioService {

	public Cardapio inserir(
			@Valid
			@NotNull(message = "O cardapio é obrigatório")
			NovoCardapio novoCardapio);
	
	public Cardapio alterar(
			@Valid
			@NotNull(message = "O cardapio é obrigatório")
			CardapioSalvo cardapioSalvo);	
	
	public Page<Cardapio> listarPor(
			@NotNull(message = "O restaurante é obrigatório")
			Restaurante restaurante, 
			Pageable paginacao);
	
	public Cardapio buscarPor(
			@NotNull(message = "O id do cardápio é obrigatório")
			@Positive(message = "O id deve ser positivo")
			Integer id);
	
	public void atualizarStatusPor(
			@NotNull(message = "O id do cardápio é obrigatório")
			@Positive(message = "O id deve ser positivo")
			Integer id, 
			@NotNull(message = "O status do cardápio é obrigatório")
			Status status);
}
