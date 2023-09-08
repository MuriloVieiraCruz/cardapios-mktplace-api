package br.com.senai.cardapiosmktplaceapi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.google.common.base.Preconditions;

import br.com.senai.cardapiosmktplaceapi.entity.Secao;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Status;
import br.com.senai.cardapiosmktplaceapi.repository.OpcoesDoCardapioRepository;
import br.com.senai.cardapiosmktplaceapi.repository.SecoesRepository;
import br.com.senai.cardapiosmktplaceapi.service.SecaoService;

public class SecaoServiceImpl implements SecaoService{
	
	@Autowired
	private SecoesRepository repository;
	
	@Autowired
	private OpcoesDoCardapioRepository opcoesDoCardapioRepository;

	@Override
	public Secao salvar(Secao secao) {
		Secao outraSecao = repository.buscarPor(secao.getNome());
		if (outraSecao != null) {
			if (secao.isPersistido()) {
				Preconditions.checkArgument(outraSecao.equals(secao),
						"O nome da seção já está em uso");
			}
		}
		
		Secao secaoSalva = repository.save(secao);
		return secaoSalva;
	}

	@Override
	public void atualizarStatusPor(Integer id, Status status) {
		Secao secaoEncontrada = repository.buscarPor(id);
		Preconditions.checkNotNull(secaoEncontrada,
				"Não foi encontrado seção para o id informado");
		Preconditions.checkArgument(secaoEncontrada.getStatus() == status,
				"O status já está salvo para a seção");
		
	}

	@Override
	public Page<Secao> listarPor(String nome, Pageable paginacao) {
		return this.repository.listarPor(nome + "%", paginacao);
	}

	@Override
	public Secao buscarPor(Integer id) {
		Secao secaoEncontrada = repository.buscarPor(id);
		Preconditions.checkNotNull(secaoEncontrada, 
				"Não foi encontrado seção para o id informado");
		Preconditions.checkArgument(secaoEncontrada.isAtiva(),
				"A seção está inativa");
		return secaoEncontrada;
	}

	@Override
	public Secao excluirPor(Integer id) {
		Secao secaoEncontrada = repository.buscarPor(id);
		Long qtdeOpcoesDoCardapioVinculadas = opcoesDoCardapioRepository.contarPor(id);
		Preconditions.checkArgument(qtdeOpcoesDoCardapioVinculadas == 0, 
				"Existem opções de cardápio vinculadas a essa seção");
		this.repository.deleteById(id);
		return secaoEncontrada;
	}

}
