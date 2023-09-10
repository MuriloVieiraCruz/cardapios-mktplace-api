package br.com.senai.cardapiosmktplaceapi.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.senai.cardapiosmktplaceapi.entity.Cardapio;
import br.com.senai.cardapiosmktplaceapi.entity.Restaurante;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public interface CardapioService {

	public Cardapio salvar(
			@NotNull(message = "O cardapio é obrigatório")
			Cardapio cardapio);
	
	public void atualizar(
			@NotBlank(message = "O nome é obrigatório")
			String nome,
			@NotBlank(message = "A descrição é obrigatória")
			String descricao,
			@NotNull(message = "O status é obrigatório")
			Status status); 
	
	public Page<Cardapio> listarPor(
			@NotNull(message = "O restaurante é obrigatório")
			Restaurante restaurante, 
			Pageable paginacao);
	
	public Cardapio buscarPor(
			@NotNull(message = "O id do cardápio é obrigatório")
			Integer id);
	
	public void atualizarStatusPor(
			@NotNull(message = "O id do cardápio é obrigatório")
			Integer id, 
			@NotNull(message = "O status do cardápio é obrigatório")
			Status status);
}
