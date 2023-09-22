package br.com.senai.cardapiosmktplaceapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OpcaoCarregada {
	
	@NotBlank(message = "O id da opção é obrigatório")
	private String nome;
	
	@NotNull(message = "O id da opção é obrigatório")
	@Positive(message = "O id da opção é obrigatório")
	private Integer idDoRestaurante;
	
	@NotNull(message = "O id da opção é obrigatório")
	@Positive(message = "O id da opção é obrigatório")
	private Integer idDaCategoria;

}
