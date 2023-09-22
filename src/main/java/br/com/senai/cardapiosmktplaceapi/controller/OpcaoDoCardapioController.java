package br.com.senai.cardapiosmktplaceapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.senai.cardapiosmktplaceapi.service.OpcaoDoCardapioService;

@RestController
@RequestMapping("/opcao_do_cardapio")
public class OpcaoDoCardapioController {

	@Autowired
	@Qualifier("opcaoDoCardapioServiceProxy")
	private OpcaoDoCardapioService service;
	
	
}
