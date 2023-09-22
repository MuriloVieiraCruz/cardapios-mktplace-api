package br.com.senai.cardapiosmktplaceapi.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.senai.cardapiosmktplaceapi.dto.NovaOpcaoRequest;
import br.com.senai.cardapiosmktplaceapi.entity.OpcaoDoCardapio;
import br.com.senai.cardapiosmktplaceapi.service.OpcaoDoCardapioService;

@RestController
@RequestMapping("/opcao_do_cardapio")
public class OpcaoDoCardapioController {

	@Autowired
	@Qualifier("opcaoDoCardapioServiceProxy")
	private OpcaoDoCardapioService service;
	
	@PostMapping
	private ResponseEntity<?> inserir(@RequestBody NovaOpcaoRequest novaOpcaoRequest) {
		OpcaoDoCardapio opcaoDoCardapioSalva = service.inserir(novaOpcaoRequest);
		return ResponseEntity.created(URI.create("/opcao_do_cardapio/id/" + 
		opcaoDoCardapioSalva.getOpcao().getId() + "/cardapio" + opcaoDoCardapioSalva.getCardapio().getId())).build();
	}
}
