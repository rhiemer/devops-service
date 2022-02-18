package br.gov.rj.detran.devops.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe para boot da aplicação
 * @author redhat
 *
 */
@SpringBootApplication
public class DetranDevopsSpringBootApplication {

	/**
	 * Inicializa a aplicação
	 * @param args Parametros de inicialização
	 */
	public static void main(String[] args) {
		SpringApplication.run(DetranDevopsSpringBootApplication.class, args);
	}

}
