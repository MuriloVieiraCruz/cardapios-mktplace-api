package br.com.senai.cardapiosmktplaceapi.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Preconditions;

import br.com.senai.cardapiosmktplaceapi.dto.NovaOpcaoRequest;
import br.com.senai.cardapiosmktplaceapi.entity.Cardapio;
import br.com.senai.cardapiosmktplaceapi.entity.Opcao;
import br.com.senai.cardapiosmktplaceapi.entity.OpcaoDoCardapio;
import br.com.senai.cardapiosmktplaceapi.service.OpcaoDoCardapioService;

@RestController
@RequestMapping("/opcao_do_cardapio")
public class OpcaoDoCardapioController {

	@Autowired
	@Qualifier("opcaoDoCardapioServiceProxy")
	private OpcaoDoCardapioService service;
	
	@Autowired
	private MapConverter converter;
	
	@PostMapping
	private ResponseEntity<?> inserir(@RequestBody NovaOpcaoRequest novaOpcaoRequest) {
		OpcaoDoCardapio opcaoDoCardapioSalva = service.inserir(novaOpcaoRequest);
		return ResponseEntity.created(URI.create("/opcao_do_cardapio/id/" + 
		opcaoDoCardapioSalva.getOpcao().getId() + "/cardapio" + opcaoDoCardapioSalva.getCardapio().getId())).build();
	}
	
	@PutMapping
	private ResponseEntity<?> alterar(@RequestBody OpcaoDoCardapio opcaoDoCardapio) {
		Preconditions.checkNotNull(opcaoDoCardapio.isPersistido(),
				"A opção do cradápio deve conter um Id");
		OpcaoDoCardapio opcaoDoCardapioSalva = service.atualizar(opcaoDoCardapio);
		return ResponseEntity.ok(converter.toJsonMap(opcaoDoCardapioSalva));	
	}
	
	@GetMapping("/opcao/{opcao}/cardapio/{cardapio}")
	private ResponseEntity<?> buscarPor(@RequestParam("opcao") Opcao opcao,
			@RequestParam("cardapio") Cardapio cardapio) {
		OpcaoDoCardapio opcaDoCardapioEncontrada = service.buscarPor(opcao, cardapio);
		return ResponseEntity.ok(converter.toJsonMap(opcaDoCardapioEncontrada));
	}
}
