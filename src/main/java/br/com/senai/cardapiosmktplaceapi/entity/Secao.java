package br.com.senai.cardapiosmktplaceapi.entity;

import br.com.senai.cardapiosmktplaceapi.entity.enums.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "Secao")
@Table(name = "secoes")
public class Secao {
	
	@Id
	@Column(name = "id")
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Size(min = 3, max = 100, message = "O nome da seção deve conter entre 3 e 100 caracteres")
	@NotBlank(message = "O nome da seção é obrigatório")
	@Column(name = "nome")
	private String nome;
	
	@Enumerated(value = EnumType.STRING)
	@NotNull(message = "O status é obrigatório")
	@Column(name = "status")
	private Status status;

}
