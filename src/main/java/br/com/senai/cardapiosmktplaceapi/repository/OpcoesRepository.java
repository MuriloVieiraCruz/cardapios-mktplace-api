package br.com.senai.cardapiosmktplaceapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.senai.cardapiosmktplaceapi.entity.Categoria;
import br.com.senai.cardapiosmktplaceapi.entity.Opcao;
import br.com.senai.cardapiosmktplaceapi.entity.Restaurante;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Status;
import br.com.senai.cardapiosmktplaceapi.entity.enums.TipoDeCategoria;

@Repository
public interface OpcoesRepository extends JpaRepository<Opcao, Integer>{

	@Query(value = 
			"SELECT o FROM Opcao o WHERE o.id = :id")
	public Opcao buscarPor(Integer id);
	
	@Query(value = 
			"SELECT c "
			+ "FROM Categoria c "
			+ "WHERE Upper(c.nome) = Upper(:nome) "
			+ "AND c.tipo = :tipo")
	public Opcao buscarPor(String nome, TipoDeCategoria tipo);
	
	@Query(value = 
			"SELECT o "
			+ "FROM Opcao o "
			+ "JOIN FETCH o.categoria "
			+ "JOIN FETCH o.restaurante "
			+ "WHERE (Upper(o.nome) like Upper(:nome)) "
			+ "JOIN FETCH (categoria = :categoria OR :categoria IS NULL) AND "
			+ "JOIN FETCH (restaurante = :restaurante OR :restaurante IS NULL) ",	
			countQuery = "SELECT o "
					+ "FROM Opcao o "
					+ "JOIN FETCH o.categoria "
					+ "JOIN FETCH o.restaurante "
					+ "WHERE (Upper(o.nome) like Upper(:nome)) "
					+ "JOIN FETCH (categoria = :categoria OR :categoria IS NULL) AND "
					+ "JOIN FETCH (restaurante = :restaurante OR :restaurante IS NULL) ")
	public Page<Opcao> listarPor(String nome, Categoria categoria, Restaurante restaurante, Pageable paginacao);
	
	@Modifying
	@Query(value = 
			"UPDATE Opcao o SET o.status = :status WHERE o.id = :id")
	public void atualizarPor(Integer id, Status status);

}
