package br.com.senai.cardapiosmktplaceapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.senai.cardapiosmktplaceapi.entity.Cardapio;
import br.com.senai.cardapiosmktplaceapi.entity.Opcao;
import br.com.senai.cardapiosmktplaceapi.entity.OpcaoDoCardapio;
import br.com.senai.cardapiosmktplaceapi.entity.composite.OpcaoDoCardapioId;

@Repository
public interface OpcoesDoCardapioRepository extends JpaRepository<OpcaoDoCardapio, OpcaoDoCardapioId>{

	@Query(value = 
			"SELECT Count(odc) "
			+ "FROM OpcaoDoCardapio odc "
			+ "WHERE odc.secao.id = :id")
	public Long contarPor(Integer id);
	
	@Query(value = 
			"SELECT Count(odc) "
			+ "FROM OpcaoDoCardapio odc "
			+ "WHERE odc.opcao.id = :id")
	public Long contarOpcaoPor(Integer id);
	
	@Query(value = 
			"SELECT Count(odc) "
			+ "FROM OpcaoDoCardapio odc "
			+ "WHERE odc.opcao = :opcao "
			+ "AND odc.cardapio = :cardapio")
	public Long contarPor(@Param("opcao") Opcao opcao, @Param("cardapio") Cardapio cardapio);
	
	@Query(value = 
			"SELECT odc "
			+ "FROM OpcaoDoCardapio odc "
			+ "JOIN FETCH odc.opcao o "
			+ "JOIN FETCH odc.cardapio c "
			+ "WHERE odc.opcao = :opcao "
			+ "AND odc.cardapio = :cardapio")
	public OpcaoDoCardapio buscarPor(@Param("opcao") Opcao opcao, @Param("cardapio") Cardapio cardapio);	
}
