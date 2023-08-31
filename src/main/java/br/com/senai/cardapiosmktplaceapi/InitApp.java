package br.com.senai.cardapiosmktplaceapi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import br.com.senai.cardapiosmktplaceapi.entity.Categoria;
import br.com.senai.cardapiosmktplaceapi.entity.enums.TipoDeCategoria;
import br.com.senai.cardapiosmktplaceapi.repository.CategoriaRepository;

@SpringBootApplication
public class InitApp {
	
	@Autowired
	private CategoriaRepository repository;

	public static void main(String[] args) {
		
		SpringApplication.run(InitApp.class, args);
	}
	
	@Bean
	public CommandLineRunner commandLineRunner() {
		return args -> {
			
			Categoria categ = new Categoria();
			categ.setNome("Temakeria");
			categ.setTipo(TipoDeCategoria.RESTAURANTE);
			repository.save(categ);
		};
	}
	
}
