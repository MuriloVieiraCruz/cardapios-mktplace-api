package br.com.senai.cardapiosmktplaceapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.senai.cardapiosmktplaceapi.entity.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer>{

}
