package br.gov.rj.detran.devops.service.test;

import static org.junit.Assert.assertNotNull;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import br.gov.rj.detran.devops.service.domain.PocPipeline;
import br.gov.rj.detran.devops.service.test.abstracts.TestCrud;

/**
 * Testes do controller PocPipelineController
 * @author redhat
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class PocPipelineControllerTest {

	private static final String URL = "http://localhost:%s/devops-service/api/poc-pipeline";

	@Autowired
	RestTemplateBuilder restTemplateBuilder;

	@Rule
	public ExpectedException exceptionRule = ExpectedException.none();

	@LocalServerPort
	private int port;

	private RestTemplate restTemplate;

	private String url;

	private PocPipeline pocPipeline;

	private PocPipeline pocPipelineUpdate;

	private TestCrud<PocPipeline, String, PocPipeline> testCrud;

	@Before
	public void before() {
		this.url = String.format(URL, port);
		this.restTemplate = restTemplateBuilder.errorHandler(new RestTemplateErrorHandler()).build();

		this.pocPipeline = new PocPipeline();
		this.pocPipeline.setDescricao(UUID.randomUUID().toString());

		this.pocPipelineUpdate = new PocPipeline();
		this.pocPipelineUpdate.setDescricao(UUID.randomUUID().toString());

		testCrud = new TestCrud<PocPipeline, String, PocPipeline>() {
			@Override
			protected String id(PocPipeline objSave, PocPipeline objOrigin) {
				return objSave.getId();
			}

			@Override
			protected PocPipeline save(PocPipeline obj) {
				return restTemplate.postForObject(url, obj, PocPipeline.class);
			}

			@Override
			protected void validSave(PocPipeline objSave, PocPipeline objOrigin) {
				assertNotNull(objSave.getId());
			}

			@Override
			protected PocPipeline find(String id, PocPipeline obj, PocPipeline objSave) {
				return restTemplate.getForObject(String.format("%s/%s", url, objSave.getId()), PocPipeline.class);
			}

			@Override
			protected void validFind(PocPipeline objFind, PocPipeline objSave, PocPipeline objOrigin) {
				assertEquals(objSave.getId(), objFind.getId());
				assertEquals(objOrigin.getDescricao(), objFind.getDescricao());
				assertNotNull(objFind.getCadastro());
				assertNotNull(objFind.getUltimaAlteracao());
			}

			@Override
			protected Collection<PocPipeline> findAll(String id, PocPipeline obj, PocPipeline objSave) {
				return Arrays.asList(restTemplate.getForObject(url, PocPipeline[].class));
			}

			@Override
			protected boolean getFindAll(PocPipeline objList, PocPipeline objFind, PocPipeline objSave,
					PocPipeline objOrigin) {
				return objList.getId().equals(objSave.getId());
			}

			@Override
			protected PocPipeline prepareUpdate(PocPipeline objFind, PocPipeline objSave, PocPipeline objOrigin) {
				return pocPipelineUpdate;
			}

			@Override
			protected void update(PocPipeline objUpdate, PocPipeline objFind, PocPipeline objSave,
					PocPipeline objOrigin) {
				restTemplate.put(String.format("%s/%s", url, objFind.getId()), objUpdate);
			}

			@Override
			protected void validUpdate(PocPipeline objFindUpdate, PocPipeline objUpdate, PocPipeline objFind,
					PocPipeline objSave, PocPipeline objOrigin) {
				assertEquals(objFind.getId(), objFindUpdate.getId());
				assertEquals(pocPipelineUpdate.getDescricao(), objFindUpdate.getDescricao());
				assertEquals(objFind.getCadastro(), objFindUpdate.getCadastro());
			}

			@Override
			protected void delete(String id, PocPipeline objFindUpdate) {
				restTemplate.delete(String.format("%s/%s", url, id));
				exceptionRule.expect(RestTemplateErrorHandlerException.class);
			}

		};
	}

	@Test
	public void testCrud() {
		testCrud.run(this.pocPipeline);
	}

	/**
	 * Gera uma exceção quando o httpCode do cliente Rest não estiver entre 200 e 299
	 * @author redhat
	 *
	 */
	public class RestTemplateErrorHandler extends DefaultResponseErrorHandler {

		@Override
		public void handleError(ClientHttpResponse response) throws IOException {
			if (!response.getStatusCode().is2xxSuccessful()) {
				try (BufferedReader reader = new BufferedReader(new InputStreamReader(response.getBody()))) {
					String httpBodyResponse = reader.lines().collect(Collectors.joining(""));
					throw new RestTemplateErrorHandlerException(response.getStatusCode(), httpBodyResponse);
				}
			}
		}
	}

	private static class RestTemplateErrorHandlerException extends RuntimeException {

		/**
		 * 
		 */
		private static final long serialVersionUID = -6998783984324460430L;

		public RestTemplateErrorHandlerException(HttpStatus statusCode, String error) {
			super(String.format("Erro %s ao chamar o serviço rest:%s", statusCode, error));
		}

	}

}
