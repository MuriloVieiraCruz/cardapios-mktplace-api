package br.com.senai.cardapiosmktplaceapi.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import br.com.senai.cardapiosmktplaceapi.dto.OpcaoCarregada;
import br.com.senai.cardapiosmktplaceapi.entity.Categoria;
import br.com.senai.cardapiosmktplaceapi.entity.Opcao;
import br.com.senai.cardapiosmktplaceapi.entity.Restaurante;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Status;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Validated
public interface OpcaoService {

	public Opcao salvar(
			@NotNull(message = "A opcao não pode ser nula")
			Opcao opcao);
	
	public void atualizarStatusPor(
			@NotNull(message = "O id é obrigatório")
			@Positive(message = "O id deve ser positivo")
			Integer id, 
			@NotNull(message = "O status é obrigatório")
			Status status);
	
	public Page<OpcaoCarregada> listarPor(
			@NotNull(message = "A opção é obrigatória")
			String nome,
			Categoria categoria,
			Restaurante restaurante,
			Pageable page);
	
	public Opcao buscarPor(
			@NotNull(message = "O id é obrigatório")
			@Positive(message = "O id deve ser positivo")
			Integer id);
	
	public Opcao excluir(
			@NotNull(message = "O id é obrigatório")
			@Positive(message = "O id deve ser positivo")
			Integer id);
}
