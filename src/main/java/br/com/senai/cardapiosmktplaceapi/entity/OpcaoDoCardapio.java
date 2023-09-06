package br.com.senai.cardapiosmktplaceapi.entity;

import java.math.BigDecimal;

import br.com.senai.cardapiosmktplaceapi.entity.composite.OpcaoDoCardapioId;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Confirmacao;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Status;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "OpcaoDoCardapio")
@Table(name = "opcoes_cardapios")
public class OpcaoDoCardapio {
	
	@EmbeddedId
	@EqualsAndHashCode.Include
	@NotNull(message = "O id da opção do cardápio é obrigatório")
	private OpcaoDoCardapioId id;
	
	@DecimalMin(value = "0.0", inclusive = true, message = "O preço deve ser posistivo")
	@Digits(integer = 9, fraction = 2, message = "O preço de desconto deve possuir o formato 'NNNNNNNNN.NN'")
	@NotNull(message = "O preço não pode ")
	@Column(name = "preco")
	private BigDecimal preco;
	
	@Enumerated(value = EnumType.STRING)
	@NotNull(message = "O status é obrigatório")
	@Column(name = "status")
	private Status  status;
	
	@Enumerated(value = EnumType.STRING)
	@NotNull(message = "A recomendação é obrigatória")
	@Column(name = "recomendado")
	private Confirmacao recomendado;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@NotNull(message = "A seção é obrigatória")
	@JoinColumn(name = "id_secao")
	private Secao secao;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("idDoCardapio")
	@JoinColumn(name = "id_cardapio")
	@NotNull(message = "A cardápio é obrigatória")
	private Cardapio cardapio;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("idDaOpcao")
	@JoinColumn(name = "id_opcao")
	@NotNull(message = "A opção é obrigatória")
	private Opcao opcao;
	
	public OpcaoDoCardapio() {
		this.status = Status.A;
	}
	
	public boolean isPersistido() {
		return getId() != null 
				&& getId().getIdDaOpcao() > 0
				&& getId().getIdDoCardapio() > 0;
	}
}
