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

@Repository
public interface OpcoesRepository extends JpaRepository<Opcao, Integer>{

	@Query(value = 
			"SELECT o "
			+ "FROM Opcao o "
			+ "JOIN FETCH o.categoria "
			+ "JOIN FETCH o.restaurante "
			+ "WHERE o.id = :id")
	public Opcao buscarPor(Integer id);
	
	@Query(value = 
			"SELECT o " 
			+ "FROM Opcao o "
			+ "WHERE Upper(o.nome) = Upper(:nome)")
	public Opcao buscarPor(String nome);
	
	@Query(value = 
			"SELECT o "
			+ "FROM Opcao o "
			+ "JOIN FETCH o.categoria c "
			+ "JOIN FETCH o.restaurante r "
			+ "WHERE (Upper(o.nome) LIKE Upper(:nome)) "
			+ "AND (:categoria IS NULL OR c = :categoria) "
			+ "AND (:restaurante IS NULL OR r = :restaurante) "
			+ "ORDER BY o.nome",
		    countQuery = "SELECT o "
					+ "FROM Opcao o "
					+ "JOIN FETCH o.categoria c "
					+ "JOIN FETCH o.restaurante r "
					+ "WHERE (Upper(o.nome) LIKE Upper(:nome)) "
					+ "AND (:categoria IS NULL OR c = :categoria) "
					+ "AND (:restaurante IS NULL OR r = :restaurante) ")	
	public Page<Opcao> listarPor(String nome, Categoria categoria, Restaurante restaurante, Pageable paginacao);
	
	@Modifying
	@Query(value = 
			"UPDATE Opcao o SET o.status = :status WHERE o.id = :id")
	public void atualizarPor(Integer id, Status status);

}
