package br.com.senai.cardapiosmktplaceapi.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;

import br.com.senai.cardapiosmktplaceapi.dto.CardapioSalvo;
import br.com.senai.cardapiosmktplaceapi.dto.NovaOpcaoCardapio;
import br.com.senai.cardapiosmktplaceapi.dto.NovoCardapio;
import br.com.senai.cardapiosmktplaceapi.entity.Cardapio;
import br.com.senai.cardapiosmktplaceapi.entity.Opcao;
import br.com.senai.cardapiosmktplaceapi.entity.OpcaoDoCardapio;
import br.com.senai.cardapiosmktplaceapi.entity.Restaurante;
import br.com.senai.cardapiosmktplaceapi.entity.Secao;
import br.com.senai.cardapiosmktplaceapi.entity.composite.OpcaoDoCardapioId;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Status;
import br.com.senai.cardapiosmktplaceapi.repository.CardapiosRepository;
import br.com.senai.cardapiosmktplaceapi.repository.OpcoesRepository;
import br.com.senai.cardapiosmktplaceapi.repository.RestaurantesRepository;
import br.com.senai.cardapiosmktplaceapi.repository.SecoesRepository;
import br.com.senai.cardapiosmktplaceapi.service.CardapioService;

@Service
public class CardapioServiceImpl implements CardapioService{
	
	@Autowired
	private CardapiosRepository repository;
	
	@Autowired
	private OpcoesRepository opcoesRepository;
	
	@Autowired
	private SecoesRepository secoesRepository;
	
	@Autowired
	private RestaurantesRepository restauranteRepository;
	
	private Restaurante getRestaurante(NovoCardapio novoCardapio) {
		Preconditions.checkNotNull(novoCardapio.getRestaurante(), 
				"O restaurante é obrigatório");
		Restaurante restaurante = restauranteRepository.buscarPor(novoCardapio.getRestaurante().getId());
		Preconditions.checkNotNull(restaurante, 
				"O restaurante '" + novoCardapio.getRestaurante().getId() + "' não foi salvo");
		Preconditions.checkArgument(restaurante.isAtivo(), 
				"O restaurante está inativo");
		
		return restaurante;
	}
	
	private Opcao getOpcaoPor(Integer idDaOpcao, Restaurante restaurante) {
		Opcao opcao = opcoesRepository.buscarPor(idDaOpcao);
		Preconditions.checkNotNull(opcao, 
				"Não existe opção vinculada ao id '" + idDaOpcao + "'");
		Preconditions.checkArgument(opcao.isAtiva(), 
				"A opção está inativa");
		Preconditions.checkArgument(opcao.getRestaurante().equals(restaurante), 
				"A opção '" + idDaOpcao + "' não pertence ao cardápio do restaurante");
		return opcao;
	}
	
	private Secao getSecaoPor(NovaOpcaoCardapio novaOpcao) {
		Preconditions.checkNotNull(novaOpcao.getSecao(), 
				"A seção da oção é obrigatória");
		
		Secao secao = secoesRepository.findById(novaOpcao.getSecao().getId()).get();
		Preconditions.checkNotNull(secao, 
				"Não existe seção vinculada ao id '" + novaOpcao.getSecao().getId() + "'");
		Preconditions.checkArgument(secao.isAtiva(),
				"A seção está inativa");
		
		return secao;
	}
	
	private void validarDuplicidade(List<NovaOpcaoCardapio> opcoesDoCardapio) {
		for(NovaOpcaoCardapio novaOpcao: opcoesDoCardapio) {
			int qtdeDeOcorrencias = 0;
			for (NovaOpcaoCardapio outraOpcao: opcoesDoCardapio) {
				if (novaOpcao.getIdDaOpcao().equals(outraOpcao.getIdDaOpcao())) {
					qtdeDeOcorrencias++;
				}
			}
			Preconditions.checkArgument(qtdeDeOcorrencias == 1,
					"A opção '" + novaOpcao.getIdDaOpcao() + "' está duplicada no cardápio");
		}
		
	}

	@Override
	public Page<Cardapio> listarPor(Restaurante restaurante, Pageable paginacao) {
		Page<Cardapio> cardapios = repository.listarPorRestaurante(restaurante, paginacao);
		for (Cardapio cardapio: cardapios.getContent()) {
		 	this.atualizarPrecoDas(cardapio.getOpcoes());
		}
		return cardapios;
	}

	@Override
	public Cardapio buscarPor(Integer id) {
		Cardapio cardapioEncontrado = repository.buscarPor(id);
		Preconditions.checkNotNull(cardapioEncontrado,
				"Não foi encontrado cardápio para o id informado");
		Preconditions.checkArgument(cardapioEncontrado.isAtiva(),
				"O cardápio está inativo");
		
		this.atualizarPrecoDas(cardapioEncontrado.getOpcoes());
		return cardapioEncontrado;
	}

	@Override
	public void atualizarStatusPor(Integer id, Status status) {
		Cardapio cardapioEncontrado = repository.buscarPor(id);
		Preconditions.checkNotNull(cardapioEncontrado,
				"Não existe cardápio vinculado ao id informado");
		Preconditions.checkArgument(cardapioEncontrado.getStatus() == status,
				"O status já está salvo para o restaurante");
		this.repository.atualizarPor(id, status);
	}

	@Override
	public Cardapio inserir(NovoCardapio novoCardapio) {
		Restaurante restaurante = getRestaurante(novoCardapio);
		Cardapio cardapio = new Cardapio();
		cardapio.setNome(novoCardapio.getNome());
		cardapio.setDescricao(novoCardapio.getDescricao());
		cardapio.setRestaurante(restaurante);
		Cardapio cardapioSalvo = repository.save(cardapio);
		this.validarDuplicidade(novoCardapio.getOpcoes());
		for(NovaOpcaoCardapio novaOpcao: novoCardapio.getOpcoes()) {
			Opcao opcao = getOpcaoPor(novaOpcao.getIdDaOpcao(), restaurante);
			Secao seccao = getSecaoPor(novaOpcao);
			OpcaoDoCardapioId id = new OpcaoDoCardapioId(cardapioSalvo.getId(), opcao.getId());
			OpcaoDoCardapio opcaoDoCardapio = new OpcaoDoCardapio();
			opcaoDoCardapio.setId(id);
			opcaoDoCardapio.setCardapio(cardapioSalvo);
			opcaoDoCardapio.setOpcao(opcao);
			opcaoDoCardapio.setSecao(seccao);
			opcaoDoCardapio.setPreco(novaOpcao.getPreco());
			opcaoDoCardapio.setRecomendado(novaOpcao.getRecomendacao());
			cardapioSalvo.getOpcoes().add(opcaoDoCardapio);
			Cardapio cardapiom = this.repository.saveAndFlush(cardapioSalvo);
		}
		return repository.buscarPor(cardapioSalvo.getId());
	}

	@Override
	public Cardapio alterar(CardapioSalvo cardapioSalvo) {
		Restaurante restaurante = restauranteRepository.buscarPor(cardapioSalvo.getRestaurante().getId());
		Cardapio cardapio = repository.buscarPor(cardapioSalvo.getId());
		cardapio.setNome(cardapioSalvo.getNome());
		cardapio.setDescricao(cardapioSalvo.getDescricao());
		cardapio.setRestaurante(restaurante);
		cardapio.setStatus(cardapioSalvo.getStatus());
		Cardapio cardapioAtualizado = repository.saveAndFlush(cardapio);
		return buscarPor(cardapioAtualizado.getId());
	}
	
	private void atualizarPrecoDas(List<OpcaoDoCardapio> opcoesDoCardapio) {
		for(OpcaoDoCardapio opcaoDoCardapio: opcoesDoCardapio) {
			if (opcaoDoCardapio.getOpcao().isEmPromocao()) {
				BigDecimal divisor = new BigDecimal(100);
				
				BigDecimal percentualDeDesconto = 
						opcaoDoCardapio.getOpcao().getPercentualDeDesconto();
				
				BigDecimal valorDescontado = opcaoDoCardapio.getPreco()
						.multiply(percentualDeDesconto).divide(divisor);
				
				BigDecimal preco = opcaoDoCardapio.getPreco()
						.subtract(valorDescontado).setScale(2, RoundingMode.CEILING);
				opcaoDoCardapio.setPreco(preco);
				
			}
		}
	}

}
