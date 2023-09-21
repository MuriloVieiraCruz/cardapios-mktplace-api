package br.com.senai.cardapiosmktplaceapi.service.proxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.senai.cardapiosmktplaceapi.entity.Secao;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Status;
import br.com.senai.cardapiosmktplaceapi.service.SecaoService;

@Service
public class SecaoServiceProxy implements SecaoService{
	
	@Autowired
	@Qualifier("secaoServiceImpl")
	private SecaoService service;

	@Override
	public Secao salvar(Secao secao) {
		return service.salvar(secao);
	}

	@Override
	public void atualizarStatusPor(Integer id, Status status) {
		this.service.atualizarStatusPor(id, status);
	}

	@Override
	public Page<Secao> listarPor(String nome, Pageable paginacao) {
		return service.listarPor(nome, paginacao);
	}

	@Override
	public Secao buscarPor(Integer id) {
		return service.buscarPor(id);
	}

	@Override
	public Secao excluirPor(Integer id) {
		return service.excluirPor(id);
	}

}
