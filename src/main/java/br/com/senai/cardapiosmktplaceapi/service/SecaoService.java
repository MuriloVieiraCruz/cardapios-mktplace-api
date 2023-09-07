package br.com.senai.cardapiosmktplaceapi.service;

import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;

import br.com.senai.cardapiosmktplaceapi.entity.Secao;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Validated
public interface SecaoService {
	
	public Secao salvar(
			@NotNull(message = "A seção é obrigatório")
			Secao secao);
	
	public void atualizarStatusPor(
			@NotNull(message = "O id é obrigatório")
			@Positive(message = "O id precisa ser positivo")
			Integer id,
			@NotNull(message = "O status é obrigatório")
			Status status);
	
	public Page<Secao> listarPor(
			@NotBlank(message = "O nome é obrigatório")
			String nome);
	
	public Secao buscarPor(
			@NotNull(message = "O id é obrigatório")
			@Positive(message = "O id precisa ser positivo")
			Integer id);
	
	public Secao excluirPor(
			@NotNull(message = "O id é obrigatório")
			@Positive(message = "O id precisa ser positivo")
			Integer id);

}
