package br.com.senai.cardapiosmktplaceapi;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.datatype.hibernate5.jakarta.Hibernate5JakartaModule;

@SpringBootApplication
public class InitApp {
	
//	@Autowired
//	@Qualifier("opcaoDoCardapioServiceImpl")
//	private OpcaoDoCardapioService service;
//	
//	@Autowired
//	private OpcoesDoCardapioRepository repository;
//	
//	@Autowired
//	private OpcoesRepository opcaoRepository;
//	
//	@Autowired
//	private CardapiosRepository cardRepository;
	

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
			
			System.out.println("Subiu");
		};
	}
	
}
