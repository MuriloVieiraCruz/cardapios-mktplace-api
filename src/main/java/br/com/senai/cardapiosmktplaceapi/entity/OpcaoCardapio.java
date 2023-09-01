package br.com.senai.cardapiosmktplaceapi.entity;

import java.math.BigDecimal;

import br.com.senai.cardapiosmktplaceapi.entity.enums.Confirmacao;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

@Entity(name = "OpcaoCardapio")
@Table(name = "opcoes_cardapios")
public class OpcaoCardapio {

	@DecimalMin(value = "0.0", inclusive = true, message = "O preço não pode ser menor que 0.01")
	@DecimalMax(value = "100.0", inclusive = false, message = "O preço não pode ser maior que 100.00")
	@Digits(integer = 2, fraction = 2, message = "O preço de desconto deve possuir o formato 'NN.NN'")
	@NotNull(message = "O preço não pode ")
	@Column(name = "O preço é obrigatório")
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
}
