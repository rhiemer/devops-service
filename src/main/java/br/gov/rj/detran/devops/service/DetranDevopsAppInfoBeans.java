package br.gov.rj.detran.devops.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.gov.rj.detran.devops.service.util.AppInfo;

/**
 * SpringBeans de informações da aplicação
 * 
 * @author redhat
 *
 */
@Configuration
public class DetranDevopsAppInfoBeans {

	/**
	 * Nome da aplicação em application.yaml
	 */
	@Value("${spring.application.name:null}")
	private String appName;

	/**
	 * Descrição da aplicação em application.yaml
	 */
	@Value("${spring.application.description:null}")
	private String description;

	/**
	 * Informações da aplicação
	 * 
	 * @param context contexto do spring
	 * @return AppInfo
	 */
	@Bean
	public AppInfo appInfo(ApplicationContext context) {
		return new AppInfo(context, this.appName, this.description);
	}

}
