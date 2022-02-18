package br.gov.rj.detran.devops.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.gov.rj.detran.devops.service.util.AppInfo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ExampleProperty;

/**
 * Informações da aplicação
 * 
 * @author redhat
 *
 */
@RestController
@RequestMapping("/info")
public class AppInfoController {

	@Autowired
	private AppInfo appInfo;

	/**
	 * Retorna um texto com as informações nome,host,versão da aplicação
	 * 
	 * @return 200
	 */
	@GetMapping(produces = MediaType.TEXT_PLAIN_VALUE)
	@ApiOperation("Retorna informações da aplicação e do ambiente: nome-descrição-versão-host.")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Informações da aplicação e do ambiente", examples = @io.swagger.annotations.Example(value = {
					@ExampleProperty(value = "Aplicação DevopsService - POC de uma API em spring-boot para o projeto devops do DETRAN-RJ versão 1.0.0 rodando no host rodrigohiemer-Inspiron-7560 .", mediaType = "text/plain") })) })
	public String infosApp() {
		return appInfo.info();
	}

}