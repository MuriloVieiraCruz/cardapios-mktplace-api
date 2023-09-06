package br.com.senai.cardapiosmktplaceapi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;

import br.com.senai.cardapiosmktplaceapi.entity.Categoria;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Status;
import br.com.senai.cardapiosmktplaceapi.entity.enums.TipoDeCategoria;
import br.com.senai.cardapiosmktplaceapi.repository.CategoriaRepository;
import br.com.senai.cardapiosmktplaceapi.repository.RestaurantesRepository;
import br.com.senai.cardapiosmktplaceapi.service.CategoriaService;

@Service
public class CategoriaServiceImpl implements CategoriaService{
	
	@Autowired
	private CategoriaRepository repository;
	@Autowired
	private RestaurantesRepository restauranteRepository;

	@Override
	public Categoria salvar(Categoria categoria) {
		Categoria categoriaDoBanco = repository.buscarPor(categoria.getNome(), categoria.getTipo());
		if (categoriaDoBanco != null) {
			if (categoria.isPersistido()) {
				Preconditions.checkArgument(categoriaDoBanco.equals(categoria), 
						"O nome da categoria já está em uso");
			}		
		}
		Categoria categoriaSalva = repository.save(categoria);
		return categoriaSalva;
	}

	@Override
	public void atualizarStatusPor(Integer id, Status status) {
		Categoria categoriaEncontrada = repository.buscarPor(id);
		Preconditions.checkNotNull(categoriaEncontrada,
				"Não existe categoria vinculada ao id informado");
		Preconditions.checkArgument(categoriaEncontrada.getStatus() != status,
				"O status já está salvo para a categoria");
		this.repository.atualizarPor(id, status);
		
	}

	@Override
	public Page<Categoria> listarPor(String nome, Status status, TipoDeCategoria tipo, Pageable page) {
		return repository.listarPor(nome + "%", status, tipo, page);
	}

	@Override
	public Categoria buscarPor(Integer id) {
		Categoria categoriaEncontrada = repository.buscarPor(id);
		Preconditions.checkNotNull(categoriaEncontrada,
				"Não foi encontrada nenhuma categoria com o id informado");
		Preconditions.checkNotNull(categoriaEncontrada.isAtiva(),
				"A categoria está ativa");
		return null;
	}

	@Override
	public Categoria excluirPor(Integer id) {
		Categoria categoriaParaExclusao = buscarPor(id);
		Long qtdeDeRestaurantesVinculados = restauranteRepository.contarPor(id);
		Preconditions.checkArgument(qtdeDeRestaurantesVinculados == 0,
				"Não é possivel remover pois existem restaurantes vinculados");
		repository.deleteById(categoriaParaExclusao.getId());
		return categoriaParaExclusao;
	}

}
