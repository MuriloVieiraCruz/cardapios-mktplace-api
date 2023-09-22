package br.com.senai.cardapiosmktplaceapi.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;

import br.com.senai.cardapiosmktplaceapi.dto.NovaOpcaoRequest;
import br.com.senai.cardapiosmktplaceapi.entity.Cardapio;
import br.com.senai.cardapiosmktplaceapi.entity.Opcao;
import br.com.senai.cardapiosmktplaceapi.entity.OpcaoDoCardapio;
import br.com.senai.cardapiosmktplaceapi.entity.Secao;
import br.com.senai.cardapiosmktplaceapi.entity.composite.OpcaoDoCardapioId;
import br.com.senai.cardapiosmktplaceapi.repository.CardapiosRepository;
import br.com.senai.cardapiosmktplaceapi.repository.OpcoesDoCardapioRepository;
import br.com.senai.cardapiosmktplaceapi.repository.OpcoesRepository;
import br.com.senai.cardapiosmktplaceapi.repository.SecoesRepository;
import br.com.senai.cardapiosmktplaceapi.service.OpcaoDoCardapioService;
import jakarta.transaction.Transactional;

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
	public OpcaoDoCardapio inserir(NovaOpcaoRequest novaOpcaoRequest) {
		Opcao opcaoRetornada = getOpcaoPor(novaOpcaoRequest.getNovaOpcaoCardapio().getIdDaOpcao());
		Secao secaoRetornada = getSecaoPor(novaOpcaoRequest.getNovaOpcaoCardapio().getSecao().getId(), 
				novaOpcaoRequest.getNovaOpcaoCardapio().getSecao());
		Cardapio cardapioRetornado = getCardapioPor(novaOpcaoRequest.getIdDoCardapio());
		
		OpcaoDoCardapioId id = new OpcaoDoCardapioId(opcaoRetornada.getId(), cardapioRetornado.getId());
		
		OpcaoDoCardapio opcaoDoCardapio = new OpcaoDoCardapio();
		opcaoDoCardapio.setId(id);
		opcaoDoCardapio.setPreco(novaOpcaoRequest.getNovaOpcaoCardapio().getPreco());
		opcaoDoCardapio.setRecomendado(novaOpcaoRequest.getNovaOpcaoCardapio().getRecomendado());
		opcaoDoCardapio.setOpcao(opcaoRetornada);
		opcaoDoCardapio.setCardapio(cardapioRetornado);
		opcaoDoCardapio.setSecao(secaoRetornada);
		
		OpcaoDoCardapio opcaoDoCardapioSalva = opcoesDoCardapioRepository.saveAndFlush(opcaoDoCardapio);
		
		return opcaoDoCardapioSalva;
	}

	@Override
	public OpcaoDoCardapio buscarPor(Opcao opcao, Cardapio cardapio) {
		Opcao opcaoDoBanco = getOpcaoPor(opcao.getId());
		Cardapio cardapioDoBanco = getCardapioPor(cardapio.getId());
		OpcaoDoCardapio opcaoDoCardapio = opcoesDoCardapioRepository.buscarPor(opcaoDoBanco, cardapioDoBanco);
		
		Preconditions.checkNotNull(opcaoDoCardapio, 
				"Não foi encontrado opção vinculada ao cardápio informado");
		
		this.atualizaPrecoDa(opcaoDoCardapio);
		
		return opcaoDoCardapio;
	}

	@Override
	public OpcaoDoCardapio atualizar(OpcaoDoCardapio opcaoDoCardapio) {
		
		Opcao opcaoEncontrada = getOpcaoPor(opcaoDoCardapio.getOpcao().getId());
		Cardapio cardapioEncontrado = getCardapioPor(opcaoDoCardapio.getCardapio().getId());
		getSecaoPor(opcaoDoCardapio.getSecao().getId(), opcaoDoCardapio.getSecao());
		
		OpcaoDoCardapio opcaoDoCardapioEncontrado = buscarPor(opcaoEncontrada, cardapioEncontrado);
//		Preconditions.checkArgument(opcaoDoCardapioEncontrado.getCardapio().equals(cardapioEncontrado),
//				"O cardápio informado está diferente do original");
		
		opcaoDoCardapioEncontrado.setPreco(opcaoDoCardapio.getPreco());
		opcaoDoCardapioEncontrado.setRecomendado(opcaoDoCardapio.getRecomendado());
		opcaoDoCardapioEncontrado.setStatus(opcaoDoCardapio.getStatus());		
		
		Preconditions.checkArgument(opcaoDoCardapio.getPreco() == null 
				|| opcaoDoCardapio.getPreco() != BigDecimal.ZERO,
				"O preço deve ser maior que zero");
		
		Long qtdeDeOpcoesIguais = opcoesDoCardapioRepository.contarPor(opcaoEncontrada, cardapioEncontrado);
		Preconditions.checkArgument(qtdeDeOpcoesIguais > 0,
				"A opção informada já existe para o cardápio informado");
		
		this.atualizaPrecoDa(opcaoDoCardapioEncontrado);
		OpcaoDoCardapio opcaoAtualizada = opcoesDoCardapioRepository.saveAndFlush(opcaoDoCardapioEncontrado);
		return opcaoAtualizada;
	}
	
	private Opcao getOpcaoPor(Integer idDaOpcao) {
		Opcao opcao = opcoesRepository.buscarPor(idDaOpcao);
		Preconditions.checkNotNull(opcao,
				"Não existe opção vinculada ao id informado");
		Preconditions.checkArgument(opcao.isAtiva(),
				"A opção está inativa");
		
		return opcao;
	}
	
	private Cardapio getCardapioPor(Integer idCardapio) {
		
		Cardapio cardapioEncontrado = cardapiosRepository.findById(idCardapio).get();
		
		Preconditions.checkNotNull(cardapioEncontrado, 
				"Não existe cardápio para o id informado");
		
		Preconditions.checkArgument(cardapioEncontrado.isAtiva(),
				"O cardápio informado está inativo");
		
		return cardapioEncontrado;
	}
	
	private Secao getSecaoPor(Integer idDaSecao, Secao secao) {
		
		Secao secaoEncontrada = secoesRepository.findById(idDaSecao).get();
		Preconditions.checkNotNull(secao, 
				"A seção da oção é obrigatória");
		
		Preconditions.checkNotNull(secaoEncontrada, 
				"Não existe seção vinculada ao id '" + secaoEncontrada.getId() + "'");
		Preconditions.checkArgument(secaoEncontrada.isAtiva(),
				"A seção está inativa");
		Preconditions.checkArgument(secaoEncontrada.equals(secao),
				"A seção informada está diferente da original");
		
		return secaoEncontrada;
	}
	
	@Transactional
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
