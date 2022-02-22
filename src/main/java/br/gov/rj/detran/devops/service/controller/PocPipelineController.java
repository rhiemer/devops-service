package br.gov.rj.detran.devops.service.controller;

import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.gov.rj.detran.devops.service.domain.PocPipeline;
import br.gov.rj.detran.devops.service.domain.PocPipeline.PocPipelineResponse;
import br.gov.rj.detran.devops.service.repository.PocPipelineRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Api como CRUD para a entidade poc-pipeline
 * 
 * @author redhat
 *
 */
@RestController
@RequestMapping("/poc-pipeline")
public class PocPipelineController {

	@Autowired
	private PocPipelineRepository pocPipelineRepository;

	/**
	 * Busca no Repositório uma entidade PocPipeline pelo id
	 * 
	 * @param id identificador da entidade PocPipeline
	 * @return PocPipeline
	 * @throws ResponseStatusException 404-NotFound
	 */
	private PocPipeline find(final String id) {
		try {
			return pocPipelineRepository.findById(id).get();
		} catch (NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Id %s não encontrao", id));
		}
	}

	/**
	 * Busca todas as PocPipeline do Repositório
	 * 
	 * @return 200-PocPipelines
	 */
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation("Retorna lista com todas as entidades.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Lista com todas as entidades") })
	public List<PocPipeline> list() {
		return pocPipelineRepository.findAll();
	}

	/**
	 * Busca no Repositório uma entidade PocPipeline pelo id
	 * 
	 * @param id identificador da entidade PocPipeline
	 * @return 200-PocPipeline
	 * @throws ResponseStatusException 404-NotFound
	 */
	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation("Retorna entidade pelo ID. 404 se não econtrar")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Encontrado"),
			@ApiResponse(code = 404, message = "Id Não econtrado") })
	public PocPipeline getById(final @PathVariable String id) {
		return this.find(id);
	}

	/**
	 * Salva no Repositório a PocPipeline do body
	 * 
	 * @param body Entidade PocPipeline
	 * @return 201-Created<br>
	 *         id da entidade cadastrada
	 */
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation("Salva entidade")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Incluído com sucesso"),
			@ApiResponse(code = 400, message = "Erros de validação no body") })
	public ResponseEntity<PocPipelineResponse> save(final @Valid @NotNull @RequestBody PocPipeline body) {
		return new ResponseEntity<PocPipelineResponse>(pocPipelineRepository.save(body).response(), HttpStatus.CREATED);
	}

	/**
	 * Busca no repositorio pelo id e altera com o body
	 * 
	 * @param id   identificador da entidade PocPipeline
	 * @param body Entidade PocPipeline
	 * @throws ResponseStatusException 404-NotFound
	 * @return 204-NoContent
	 */
	@PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation("Altera entidade pelo id")
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Alterado com sucesso"),
			@ApiResponse(code = 400, message = "Erros de validação no body"),
			@ApiResponse(code = 404, message = "Id Não econtrado") })
	public Object update(final @PathVariable String id, @Valid @NotNull @RequestBody final PocPipeline body) {
		this.pocPipelineRepository.save(find(id).update(body));
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	/**
	 * Deleta no repositorio pelo id
	 * 
	 * @param id identificador da entidade PocPipeline
	 * @throws ResponseStatusException 404-NotFound
	 * @return 204-NoContent
	 */
	@DeleteMapping(path = "/{id}")
	@ApiOperation("Remove entidade pelo id")
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Removido com sucesso"),
			@ApiResponse(code = 404, message = "Id Não econtrado") })
	public Object delete(final @PathVariable String id) {
		this.pocPipelineRepository.delete(find(id));
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

}