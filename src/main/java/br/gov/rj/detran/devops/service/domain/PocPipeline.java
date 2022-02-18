package br.gov.rj.detran.devops.service.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

/**
 * Dominio da entidade do banco de dados POC_PIPELINE
 * 
 * @author redhat
 *
 */
@Entity(name = "POC_PIPELINE")
public class PocPipeline {

	@Transient
	private final PocPipelineResponse pocPipelineResponse;

	public PocPipeline() {
		this.pocPipelineResponse = new PocPipelineResponse(this);
	}

	@Id
	@Column(name = "ID")
	private String id;

	@Column(name = "DESCRICAO")
	@NotBlank
	private String descricao;

	@Column(name = "CADASTRO")
	@CreatedDate
	private LocalDateTime cadastro;

	@Column(name = "ALTERACAO")
	@LastModifiedDate
	private LocalDateTime ultimaAlteracao;

	/**
	 * Antes de salvar gera o id em uuid, a data de cadastro e a data de atualização
	 * com a data corrente.
	 */
	@PrePersist
	private void prePersistent() {
		this.id = UUID.randomUUID().toString();
		this.cadastro = LocalDateTime.now();
		this.ultimaAlteracao = LocalDateTime.now();
	}

	/**
	 * Antes de salvar altera data de atualização para a data corrente.
	 */
	@PreUpdate
	private void preUpdate() {
		this.ultimaAlteracao = LocalDateTime.now();
	}

	public String getId() {
		return id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public LocalDateTime getCadastro() {
		return cadastro;
	}

	public LocalDateTime getUltimaAlteracao() {
		return ultimaAlteracao;
	}

	/**
	 * Informações de retorno após salvar<br>
	 * <b>id</b>
	 * 
	 * @return PocPipelineResponse
	 */
	public PocPipelineResponse response() {
		return pocPipelineResponse;
	}

	/**
	 * Informações para alteração<br>
	 * <b>descricao</b>
	 * 
	 * @param pocPipeline Novas informações.
	 * @return PocPipeline
	 */
	public PocPipeline update(PocPipeline pocPipeline) {
		this.setDescricao(pocPipeline.getDescricao());
		return this;
	}

	/**
	 * Informações de retorno após salvar
	 * 
	 * @author redhat
	 *
	 */
	public static class PocPipelineResponse {

		private final PocPipeline pocPipeline;

		public PocPipelineResponse(PocPipeline pocPipeline) {
			this.pocPipeline = pocPipeline;
		}

		public String getId() {
			return pocPipeline.id;
		}

	}

}
