package br.com.senai.cardapiosmktplaceapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.senai.cardapiosmktplaceapi.entity.enums.Status;
import br.com.senai.cardapiosmktplaceapi.entity.enums.TipoDeCategoria;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "categorias")
@Entity(name = "Categoria")
public class Categoria {
	
	@Id
	@Column(name = "id")
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Size(max = 100, message = "O nome não deve conter mais de 100 caracteres")
	@NotBlank(message = "O nome da categoria é obrigatório")
	@Column(name = "nome")
	private String nome;
	
	@NotNull(message = "O tipo da categoria é obrigatório")
	@Enumerated(value = EnumType.STRING)
	@Column(name = "tipo")
	private TipoDeCategoria tipo;
	
	@NotNull(message = "O status da categoria é obrigatório")
	@Enumerated(value = EnumType.STRING)
	@Column(name = "status")
	private Status status;
	
	public Categoria() {
		this.status = Status.A;
	}
	
	@JsonIgnore
	@Transient
	public boolean isPersistido() {
		return getId() != null && getId() > 0;
	}
	
	@JsonIgnore
	@Transient
	public boolean isAtiva() {
		return getStatus() == Status.A;
	}

}
