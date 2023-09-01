package br.com.senai.cardapiosmktplaceapi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import br.com.senai.cardapiosmktplaceapi.repository.CategoriaRepository;
import br.com.senai.cardapiosmktplaceapi.repository.RestaurantesRepository;

@SpringBootApplication
public class InitApp {
	
	@Autowired
	private CategoriaRepository repository;
	
	@Autowired
	private RestaurantesRepository repositoryRest;

	public static void main(String[] args) {
		
		SpringApplication.run(InitApp.class, args);
	}
	
	@Bean
	public CommandLineRunner commandLineRunner() {
		return args -> {
			

			System.out.println("Subiu");
		};
	}
	
}
