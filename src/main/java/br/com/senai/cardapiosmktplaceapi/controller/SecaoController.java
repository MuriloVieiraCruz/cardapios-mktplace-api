package br.com.senai.cardapiosmktplaceapi.controller;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Preconditions;

import br.com.senai.cardapiosmktplaceapi.entity.Secao;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Status;
import br.com.senai.cardapiosmktplaceapi.service.SecaoService;

@RestController
@RequestMapping("/secoes")
public class SecaoController {
	
	@Autowired
	private MapConverter converter;
	
	@Autowired
	@Qualifier("secaoServiceProxy")
	private SecaoService service;
	
	@PostMapping
	public ResponseEntity<?> salvar(@RequestBody Secao secao) {
		Preconditions.checkNotNull(!secao.isPersistido(),
				"A seção não pode possuir id na inserção");
		Secao secaoSalva = service.salvar(secao);
		return ResponseEntity.created(URI.create("/secoes/id/" + secaoSalva.getId())).build();
	}
	
	@PutMapping
	private ResponseEntity<?> alterar(@RequestBody Secao secao) {
		Preconditions.checkNotNull(secao.getId(),
				"A seção deve conter um id");
		Secao secaoAlterada = service.salvar(secao);
		return ResponseEntity.ok(converter.toJsonMap(secaoAlterada));
	}
	
	@PatchMapping("/id/{id}/status/{status}")
	private ResponseEntity<?> alterarStatusPor(@PathVariable("id") Integer id, @PathVariable("status") Status status) {
		this.service.atualizarStatusPor(id, status);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/id/{id}")
	private ResponseEntity<?> buscarPor(@PathVariable("id") Integer id) {
		Secao secao = service.buscarPor(id);
		return ResponseEntity.ok(converter.toJsonMap(secao));
	}
	
	@GetMapping
	private ResponseEntity<?> listarPor(@RequestParam("nome") String nome,@RequestParam("pagina") Optional<Integer> pagina) {
		Pageable paginacao = null;
		
		if (pagina.isPresent()) {
			paginacao = PageRequest.of(pagina.get(), 15);
		} else {
			paginacao = PageRequest.of(0, 15);
		}
		
		Page<Secao> secoes = service.listarPor(nome, paginacao);
		return ResponseEntity.ok(converter.toJsonList(secoes));
	}
	
	@DeleteMapping("/id/{id}")
	private ResponseEntity<?> excluirPor(@PathVariable("id") Integer id) {
		Secao secaoExcluida = service.excluirPor(id);
		return ResponseEntity.ok(converter.toJsonMap(secaoExcluida));
	} 
	

}
