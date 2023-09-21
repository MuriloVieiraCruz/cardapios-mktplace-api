package br.com.senai.cardapiosmktplaceapi.service;

import org.springframework.validation.annotation.Validated;

import br.com.senai.cardapiosmktplaceapi.dto.NovaOpcaoCardapio;
import br.com.senai.cardapiosmktplaceapi.entity.Cardapio;
import br.com.senai.cardapiosmktplaceapi.entity.OpcaoDoCardapio;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@Validated
public interface OpcaoDoCardapioService {

	public OpcaoDoCardapio inserir(
			@Valid
			@NotNull(message = "A nova opção do cardápio é obrigatória")
			NovaOpcaoCardapio novaOpcaoCardapio,
			@Valid
			@NotNull(message = "O cardápio é obrigatório")
			Cardapio cardapio);
	
	
//	public OpcaoDoCardapio buscarPor(
//			@NotNull(message = "A opção é obrigatória")
//			Opcao opcao, 
//			@NotNull(message = "O cardápio é obrigatório")
//			Cardapio cardapio);
//	
//	public OpcaoDoCardapio atualizar(
//			@NotNull(message = "A opção do cardápio é obrigatória")
//			OpcaoDoCardapio opcaoDoCardapio);
}
