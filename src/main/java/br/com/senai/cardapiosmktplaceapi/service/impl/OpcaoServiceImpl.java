package br.com.senai.cardapiosmktplaceapi.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.google.common.base.Preconditions;

import br.com.senai.cardapiosmktplaceapi.dto.OpcaoSalva;
import br.com.senai.cardapiosmktplaceapi.entity.Categoria;
import br.com.senai.cardapiosmktplaceapi.entity.Opcao;
import br.com.senai.cardapiosmktplaceapi.entity.Restaurante;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Confirmacao;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Status;
import br.com.senai.cardapiosmktplaceapi.repository.CardapiosRepository;
import br.com.senai.cardapiosmktplaceapi.repository.CategoriaRepository;
import br.com.senai.cardapiosmktplaceapi.repository.OpcoesRepository;
import br.com.senai.cardapiosmktplaceapi.repository.RestaurantesRepository;
import br.com.senai.cardapiosmktplaceapi.service.OpcaoService;

public class OpcaoServiceImpl implements OpcaoService{
	
	@Autowired
	private OpcoesRepository repository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	@Qualifier("RestauranteServiceImpl")
	private RestaurantesRepository restauranteRepository;
	
	@Autowired
	private CardapiosRepository cardapiosRepository;

	@Override
	public Opcao salvar(Opcao opcao) {
		Opcao outraOpcao = repository.buscarPor(opcao.getNome());

		Preconditions.checkNotNull(outraOpcao,
				"A opção não pode ser nula");
		
		if (opcao.isPersistido()) {
			Preconditions.checkArgument(outraOpcao.equals(opcao),
					"O nome da opção já está em uso");
		}
		
		if (opcao.getPromocao().equals(Confirmacao.S)) {
			if (opcao.getPercentualDeDesconto().compareTo(BigDecimal.ZERO) <= 0) {
				opcao.setPercentualDeDesconto(null);
			}
		}
		
		this.categoriaRepository.buscarPor(opcao.getCategoria().getId());
		this.restauranteRepository.buscarPor(opcao.getRestaurante().getId());
		
		Opcao opcaoSalva = repository.save(opcao);
		
		return opcaoSalva;
	}

	@Override
	public void atualizarStatusPor(Integer id, Status status) {
		Opcao opcaoDoBanco = repository.buscarPor(id);
		Preconditions.checkNotNull(opcaoDoBanco,
				"Não foi encontrado opção para o id informado");
		Preconditions.checkArgument(opcaoDoBanco.getStatus() != status,
				"O status já está salvo para o opção");
		this.repository.atualizarPor(id, status);
	}

	@Override
	public Page<OpcaoSalva> listarPor(String nome, Categoria categoria, Restaurante restaurante, Pageable paginacao) {
		Categoria categoriaDoBanco = categoriaRepository.buscarPor(categoria.getId());
		Restaurante restauranteDoBanco = restauranteRepository.buscarPor(restaurante.getId());
		
		Preconditions.checkNotNull(categoriaDoBanco,
				"Não foi encontrado a categoria informada");
		
		Preconditions.checkNotNull(restauranteDoBanco,
				"Não foi encontrado o restaurante informado");
		
		Preconditions.checkArgument(categoria != null && restaurante != null,
				"É preciso informar no mínimo o restaurante ou a categoria");
		return this.repository.listarPor(nome + "%", categoria, restaurante, paginacao);
	}

	@Override
	public Opcao buscarPor(Integer id) {
		Opcao opcaoEncontrada = repository.buscarPor(id);
		Preconditions.checkNotNull(opcaoEncontrada,
				"Não foi encontrado opção para o id informado");
		Preconditions.checkArgument(opcaoEncontrada.isAtiva(),
				"A opção informada está inativa");
		return opcaoEncontrada;
	}

	@Override
	public Opcao excluir(Integer id) {
		Opcao opcaoEncontrada = repository.buscarPor(id);
		//Long qtdeDeCardapiosVinculados = cardapiosRepository.contarOpcaoPor(id);
		//Preconditions.checkArgument(qtdeDeCardapiosVinculados == 0, 
		//		"Não é possível remover pois existem cardapios vinculados");
		this.repository.deleteById(id);
		return opcaoEncontrada;
	}
	
//	private Categoria getCategoria(Integer idDaCategoria) {
//		
//		Categoria categoria = categoriaRepository.buscarPor(idDaCategoria);
//		Preconditions.checkNotNull(categoria,
//				"Não existe categoria vinculada a opção informado");
//		
//		Preconditions.checkArgument(categoria.isAtiva(),
//				"A categoria informada está inativa");
//		
//		return categoria;
//	}

}
