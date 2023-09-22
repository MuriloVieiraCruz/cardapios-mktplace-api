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

import br.com.senai.cardapiosmktplaceapi.dto.OpcaoCarregada;
import br.com.senai.cardapiosmktplaceapi.entity.Categoria;
import br.com.senai.cardapiosmktplaceapi.entity.Opcao;
import br.com.senai.cardapiosmktplaceapi.entity.Restaurante;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Status;
import br.com.senai.cardapiosmktplaceapi.service.OpcaoService;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/opcoes")
public class OpcaoController {

	@Autowired
	@Qualifier("opcaoServiceImpl")
	private OpcaoService service;
	
	@Autowired
	private MapConverter converter;
	
	@PostMapping
	private ResponseEntity<?> salvar(@RequestBody Opcao opcao) {
		Preconditions.checkNotNull(!opcao.isPersistido(),
				"A opção não pode possuir id na inserção");
		Opcao opcaoSalva = service.salvar(opcao);
		return ResponseEntity.created(URI.create("/opcoes/id/" + opcaoSalva.getId())).build();
	}
	
	@PutMapping
	private ResponseEntity<?> alterar(@RequestBody Opcao opcao) {
		Preconditions.checkNotNull(opcao.getId(),
				"A opção precisa ter um ID vinculado");
		Opcao opcaoAlterada = service.salvar(opcao);
		return ResponseEntity.ok(converter.toJsonMap(opcaoAlterada));
	}
	
	@PatchMapping("/id/{id}/status/{status}")
	private ResponseEntity<?> alterarStatusPor(@PathVariable("id") Integer id,@PathVariable("status") Status status) {
		this.service.atualizarStatusPor(id, status);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/id/{id}")
	private ResponseEntity<?> buscarPor(@PathVariable("id") Integer id) {
		Opcao opcaoEncontrada = service.buscarPor(id);
		return ResponseEntity.ok(converter.toJsonMap(opcaoEncontrada));
	}
	
	@GetMapping
	private ResponseEntity<?> listarPor(
			@RequestParam("nome")
			String nome, 
			@RequestParam("categoria")
			Categoria categoria,
			@RequestParam("restaurante")
			Restaurante restaurante,
			@RequestParam("pagina")
			Optional<Integer> pagina) {
		Pageable paginacao = null;
		if (pagina.isPresent()) {
			paginacao = PageRequest.of(pagina.get(), 15);
		} else {
			paginacao = PageRequest.of(0, 15);
		}
		
		Page<OpcaoCarregada> opcoes = service.listarPor(nome, categoria, restaurante, paginacao);
		return ResponseEntity.ok(converter.toJsonList(opcoes));
	}
	
	@Transactional
	@DeleteMapping("/id/{id}")
	private ResponseEntity<?> excluirPor(@PathVariable("id") Integer id) {
		Opcao opcaoExcluida = service.excluir(id);
		return ResponseEntity.ok(converter.toJsonMap(opcaoExcluida));
	}
}
