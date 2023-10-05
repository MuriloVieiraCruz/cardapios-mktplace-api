package br.com.senai.cardapiosmktplaceapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Notificacao {
	
	@Size(max = 100, message = "O e-mail não deve ter mais de 100 caracteres")
	@Email(message = "O e-mail é inválido")
	@NotBlank(message = "O destinatário é obrigatório")
	private String destinatario;
	
	@Size(max = 100, message = "O título não deve ter mais de 100 caracteres")
	@NotBlank(message = "O título é obrigatório")
	private String titulo;
	
	@NotBlank(message = "A mensagem é obrigatório")
	private String mensagem;
}
