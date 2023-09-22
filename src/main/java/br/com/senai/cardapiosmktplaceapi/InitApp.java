package br.com.senai.cardapiosmktplaceapi;
import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.datatype.hibernate5.jakarta.Hibernate5JakartaModule;

import br.com.senai.cardapiosmktplaceapi.entity.Cardapio;
import br.com.senai.cardapiosmktplaceapi.entity.Opcao;
import br.com.senai.cardapiosmktplaceapi.entity.OpcaoDoCardapio;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Confirmacao;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Status;
import br.com.senai.cardapiosmktplaceapi.repository.CardapiosRepository;
import br.com.senai.cardapiosmktplaceapi.repository.OpcoesDoCardapioRepository;
import br.com.senai.cardapiosmktplaceapi.repository.OpcoesRepository;
import br.com.senai.cardapiosmktplaceapi.service.OpcaoDoCardapioService;
import jakarta.transaction.Transactional;

@SpringBootApplication
public class InitApp {
	
	@Autowired
	@Qualifier("opcaoDoCardapioServiceImpl")
	private OpcaoDoCardapioService service;
	
	@Autowired
	private OpcoesDoCardapioRepository repository;
	
	@Autowired
	private OpcoesRepository opcaoRepository;
	
	@Autowired
	private CardapiosRepository cardRepository;
	

	public static void main(String[] args) {
		SpringApplication.run(InitApp.class, args);
	}
	
	@Bean
	public Hibernate5JakartaModule jsonHibernate5Module() {
		return new Hibernate5JakartaModule();
	}
	
	@Bean
	public CommandLineRunner commandLineRunner() {
		return args -> {
			
			Opcao opcao = opcaoRepository.buscarPor(203);
			Cardapio cardapio = cardRepository.buscarPor(13);
			
			OpcaoDoCardapio teste = repository.buscarPor(opcao, cardapio);
			
			BigDecimal novoValor = BigDecimal.TEN.multiply(new BigDecimal(5));

			novoValor = novoValor.setScale(2, RoundingMode.CEILING);
			
			teste.setRecomendado(Confirmacao.S);
			teste.setStatus(Status.I);
			teste.setPreco(novoValor);
			
			OpcaoDoCardapio opcaoAlterada = service.atualizar(teste);
			
			System.out.println(opcaoAlterada.getPreco());
			System.out.println(opcaoAlterada.getRecomendado());
			System.out.println(opcaoAlterada.getStatus());
			
			System.out.println("Subiu");
		};
	}
	
}
