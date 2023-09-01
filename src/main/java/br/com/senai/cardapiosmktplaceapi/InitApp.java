package br.com.senai.cardapiosmktplaceapi;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageRequest;

import br.com.senai.cardapiosmktplaceapi.entity.Categoria;
import br.com.senai.cardapiosmktplaceapi.entity.Endereco;
import br.com.senai.cardapiosmktplaceapi.entity.Restaurante;
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
			
			Categoria categori = repository.buscarPor(39);
			
			List<Restaurante> restaurantes = repositoryRest.listarPor("%rest%", categori, PageRequest.of(0, 15)).getContent();
			for (Restaurante re : restaurantes) {
				System.out.println(re.getNome());
			}
			
			Categoria categoria = repository.buscarPor(39);
			
			Restaurante rest = new Restaurante();
			
			rest.setNome("Restaurante MuriloItaliano");
			rest.setDescricao("O restaurante mais italiano do mundo");
			rest.setCategoria(categoria);
			
			Endereco e = new Endereco();
			e.setCidade("Murilandia");
			e.setBairro("Miranha");
			e.setLogradouro("abedu");
			e.setComplemento("dfdsfdf");
			rest.setEndereco(e);
			repositoryRest.save(rest);
		};
	}
	
}
