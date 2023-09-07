package br.com.senai.cardapiosmktplaceapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.senai.cardapiosmktplaceapi.entity.OpcaoDoCardapio;
import br.com.senai.cardapiosmktplaceapi.entity.composite.OpcaoDoCardapioId;

@Repository
public interface OpcoesDoCardapioRepository extends JpaRepository<OpcaoDoCardapio, OpcaoDoCardapioId>{

	@Query(value = 
			"SELECT Count(odc) "
			+ "FROM OpcaoDoCardapio odc "
			+ "WHERE odc.secao.id = :id")
	public Long contarPor(Integer id);
}
