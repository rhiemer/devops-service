package br.gov.rj.detran.devops.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import br.gov.rj.detran.devops.service.util.AppInfo;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Response;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * SpringBeans para documentação swagger dar aplicação
 * 
 * @author redhat
 *
 */
@Configuration
@EnableSwagger2
public class DetranDevopsSwagger {

	private static final String PACKAGE_CONTROLLERS = "br.gov.rj.detran.devops.service.controller";

	private static final List<Response> GLOBAL_RESPONSES = Arrays
			.asList(new ResponseBuilder().code("500").description("Erros não esperados").build());

	/**
	 * Gera Swagger da aplicação
	 * 
	 * @param appInfo contexto do spring
	 * @return Docket
	 */
	@Bean
	public Docket swagger(AppInfo appInfo) {
		return new Docket(DocumentationType.SWAGGER_2).useDefaultResponseMessages(false)
				.globalResponses(HttpMethod.GET, GLOBAL_RESPONSES).globalResponses(HttpMethod.POST, GLOBAL_RESPONSES)
				.globalResponses(HttpMethod.PUT, GLOBAL_RESPONSES).globalResponses(HttpMethod.DELETE, GLOBAL_RESPONSES)
				.select().apis(RequestHandlerSelectors.basePackage(PACKAGE_CONTROLLERS)).paths(PathSelectors.any())
				.build().apiInfo(apiInfo(appInfo));
	}

	private ApiInfo apiInfo(AppInfo appInfo) {
		return new ApiInfoBuilder().title(appInfo.getName()).description(appInfo.getDescription())
				.version(appInfo.getVersion()).build();
	}

}
