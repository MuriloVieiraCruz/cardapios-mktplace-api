package br.com.senai.cardapiosmktplaceapi.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;

import br.com.senai.cardapiosmktplaceapi.dto.NovaOpcaoCardapio;
import br.com.senai.cardapiosmktplaceapi.entity.Cardapio;
import br.com.senai.cardapiosmktplaceapi.entity.Opcao;
import br.com.senai.cardapiosmktplaceapi.entity.OpcaoDoCardapio;
import br.com.senai.cardapiosmktplaceapi.entity.Secao;
import br.com.senai.cardapiosmktplaceapi.repository.CardapiosRepository;
import br.com.senai.cardapiosmktplaceapi.repository.OpcoesDoCardapioRepository;
import br.com.senai.cardapiosmktplaceapi.repository.OpcoesRepository;
import br.com.senai.cardapiosmktplaceapi.repository.SecoesRepository;
import br.com.senai.cardapiosmktplaceapi.service.OpcaoDoCardapioService;

@Service
public class OpcaoDoCardapioServiceImpl implements OpcaoDoCardapioService{
	
	@Autowired
	private OpcoesRepository opcoesRepository;
	
	@Autowired
	private SecoesRepository secoesRepository;
	
	@Autowired
	private CardapiosRepository cardapiosRepository;
	
	@Autowired
	private OpcoesDoCardapioRepository opcoesDoCardapioRepository;

	@Override
	public OpcaoDoCardapio inserir(NovaOpcaoCardapio novaOpcaoCardapio) {
		Opcao opcao = getOpcaoPor(novaOpcaoCardapio.getIdDaOpcao());
		//Secao secao = getSecaoPor(novaOpcaoCardapio);
		
		return null;
	}

//	@Override
//	public OpcaoDoCardapio buscarPor(Opcao opcao, Cardapio cardapio) {
//		Opcao opcaoDoBanco = getOpcaoPor(opcao.getId());
//		OpcaoDoCardapio opcaoDoCardapio = opcoesDoCardapioRepository.buscarPor(opcaoDoBanco, cardapio);
//		
//		Preconditions.checkNotNull(opcaoDoCardapio, 
//				"Não foi encontrado opção vinculada ao cardápio informado");
//		
//		this.atualizaPrecoDa(opcaoDoCardapio);
//		
//		return opcaoDoCardapio;
//	}

//	@Override
//	public OpcaoDoCardapio atualizar(OpcaoDoCardapio opcaoDoCardapio) {
//		Opcao opcao = getOpcaoPor(opcaoDoCardapio.getOpcao().getId());
//		Cardapio cardapio = getCardapio(opcaoDoCardapio, opcaoDoCardapio.getCardapio());
//		Secao secao = getSecaoPor(opcaoDoCardapio, opcaoDoCardapio.getSecao());
//		
//		Preconditions.checkArgument(opcaoDoCardapio.getPreco() == null 
//				|| opcaoDoCardapio.getPreco() != BigDecimal.ZERO,
//				"O preço deve ser maior que zero");
//		
//		Long qtdeDeOpcoesIguais = opcoesDoCardapioRepository.contarPor(opcao, cardapio);
//		
//		Preconditions.checkArgument(qtdeDeOpcoesIguais > 0,
//				"A opção informada já existe para o cardápio informado");
//		
//		this.atualizaPrecoDa(opcaoDoCardapio);
//		
//		OpcaoDoCardapio opcaoAtualizada = opcoesDoCardapioRepository.atualizar(opcaoDoCardapio);
//		
//		return opcaoAtualizada;
//	}
	
	private Opcao getOpcaoPor(Integer idDaOpcao) {
		Opcao opcao = opcoesRepository.buscarPor(idDaOpcao);
		Preconditions.checkNotNull(opcao,
				"Não existe opção vinculada ao id informado");
		Preconditions.checkArgument(opcao.isAtiva(),
				"A opção está inativa");
		
		return opcao;
	}
	
	private Cardapio getCardapio(OpcaoDoCardapio opcaoDoCardapio, Cardapio cardapio) {
		
		Cardapio cardapioEncontrado = cardapiosRepository.buscarPor(opcaoDoCardapio.getCardapio().getId());
		
		Preconditions.checkNotNull(cardapioEncontrado, 
				"Não existe cardápio para o id informado");
		
		Preconditions.checkArgument(cardapioEncontrado.isAtiva(),
				"O cardápio informado está inativo");
		
		Preconditions.checkArgument(cardapioEncontrado.equals(cardapio),
				"O cardápio informado está diferente do original");
		
		return cardapioEncontrado;
	}
	
	private Secao getSecaoPor(OpcaoDoCardapio opcaoDoCardapio, Secao secao) {
		Preconditions.checkNotNull(opcaoDoCardapio.getSecao(), 
				"A seção da oção é obrigatória");
		
		Secao secaoEncontrada = secoesRepository.findById(opcaoDoCardapio.getSecao().getId()).get();
		Preconditions.checkNotNull(secaoEncontrada, 
				"Não existe seção vinculada ao id '" + opcaoDoCardapio.getSecao().getId() + "'");
		Preconditions.checkArgument(secaoEncontrada.isAtiva(),
				"A seção está inativa");
		
		Preconditions.checkArgument(secaoEncontrada.equals(secao),
				"A seção informada está diferente da original");
		
		return secaoEncontrada;
	}
	
	private void atualizaPrecoDa(OpcaoDoCardapio opcaoDoCardapio) {
		if (opcaoDoCardapio.getOpcao().isEmPromocao()) {
			BigDecimal divisor = new BigDecimal(100);
			
			BigDecimal percentualDeDesconto = opcaoDoCardapio.getOpcao().getPercentualDeDesconto();
			
			BigDecimal valorDescontado = opcaoDoCardapio.getPreco()
					.multiply(percentualDeDesconto)
					.divide(divisor);
			
			BigDecimal precoAtualizado = opcaoDoCardapio.getPreco()
					.subtract(valorDescontado)
					.setScale(2, RoundingMode.CEILING);
			
			opcaoDoCardapio.setPreco(precoAtualizado);
		}
	}

}
