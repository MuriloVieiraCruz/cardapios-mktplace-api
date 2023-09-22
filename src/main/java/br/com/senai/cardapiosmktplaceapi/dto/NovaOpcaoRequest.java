package br.com.senai.cardapiosmktplaceapi.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NovaOpcaoRequest {
	
	@NotNull(message = "A nova opção do cardápio é obrigatória")
	NovaOpcaoCardapio novaOpcaoCardapio;
	@NotNull(message = "O id do cardápio é obrigatório")
	Integer idDoCardapio;
}
