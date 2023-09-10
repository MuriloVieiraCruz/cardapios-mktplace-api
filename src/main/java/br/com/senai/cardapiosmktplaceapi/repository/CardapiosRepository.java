package br.com.senai.cardapiosmktplaceapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.senai.cardapiosmktplaceapi.entity.Cardapio;
import br.com.senai.cardapiosmktplaceapi.entity.Restaurante;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Status;

@Repository
public interface CardapiosRepository extends JpaRepository<Cardapio, Integer>{
	
	@Modifying
	@Query(value = 
			"UPDATE Cardapio c "
			+ "SET c.nome = :nome, "
			+ "c.descricao = :descricao, "
			+ "c.status = :status "
			+ "WHERE c.id = :id")
	public void atualizarPor(Integer id, String nome, String descricao, Status status);
	
	@Modifying
	@Query(value = 
			"UPDATE Cardapio c SET c.status = :status "
			+ "WHERE c.id = :id ")
	public void atualizarPor(Integer id, Status status);
	
	@Query(value = 
			"SELECT c "
			+ "FROM Cardapio c "
			+ "JOIN FETCH c.restaurante "
			+ "WHERE c.restaurante = :restaurante "
			+ "ORDER BY c.nome")
	public Page<Cardapio> listarPor(Restaurante restaurante, Pageable paginacao);
	
	@Query(value = 
			"SELECT c "
			+ "FROM Cardapio c "
			+ "WHERE c.id = :id")
	public Cardapio buscarPor(Integer id);
	
	@Query(value = 
			"SELECT c "
			+ "FROM Cardapio c "
			+ "WHERE c.nome = :nome")
	public Cardapio buscarPor(String nome);

	@Query(value = 
			"SELECT Count(c) "
			+ "FROM Cardapio c "
			+ "WHERE c.restaurante.id = :idDoRestaurante")
	public Long contarPor(Integer idDoRestaurante);
	
	@Query(value = 
			"SELECT Count(c) "
			+ "FROM Cardapio c "
			+ "WHERE c.opcao.id = :idDaOpcao")
	public Long contarOpcaoPor(Integer idDaOpcao);
}
