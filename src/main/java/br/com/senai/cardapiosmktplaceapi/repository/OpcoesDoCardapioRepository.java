package br.com.senai.cardapiosmktplaceapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
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
			+ "WHERE odc.opcao.id = :id "
			+ "AND odc.cardapio = :cardapio")
	public Long contarPor(Opcao opcao, Cardapio cardapio);
	
	@Query(value = 
			"SELECT odc FROM  OpcaoDoCardapio odc "
			+ "JOIN FETCH odc.opcao o "
			+ "JOIN FETCH odc.secao s "
			+ "WHERE odc.opcao = :opcao "
			+ "AND odc.secao = :secao")
	public OpcaoDoCardapio buscarPor(Opcao opcao, Cardapio cardapio);
	
	@Modifying
	@Query(value = 
			"UPDATE OpcaoDoCardapio odc "
			+ "SET odc.preco = CASE WHEN :promocao = 'S' "
			+ "THEN odc.opcao.preco - (odc.opcao.preco * :percentualDeDesconto / 100) "
			+ "ELSE odc.opcao.preco END , "
			+ "JOIN FETCH odc.opcao o "
			+ "JOIN FETCH odc.secao s "
			+ "AND odc.recomendado = :recomendado"
			+ "AND odc.opcao = opcao "
			+ "AND odc.secao = secao "
			+ "AND odc.cardapio = cardapio ")
	public OpcaoDoCardapio atualizar(OpcaoDoCardapio opcaoDoCardapio);
	
}
