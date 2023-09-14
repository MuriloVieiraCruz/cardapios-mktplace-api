package br.com.senai.cardapiosmktplaceapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OpcaoSalva {
	
	private String nome;
	
	private Integer idDoRestaurante;
	
	private Integer idDaCategoria;

}
