package br.gov.rj.detran.devops.service.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.gov.rj.detran.devops.service.domain.PocPipeline;
import br.gov.rj.detran.devops.service.repository.PocPipelineRepository;
import br.gov.rj.detran.devops.service.test.abstracts.TestCrud;

/**
 * Testes do repositorio PocPipelineRepository
 * 
 * @author redhat
 *
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class PocPipelineRepositoryTest {

	@Autowired
	private PocPipelineRepository pocPipelineRepository;

	@Rule
	public ExpectedException exceptionRule = ExpectedException.none();

	private PocPipeline pocPipeline;

	private PocPipeline pocPipelineUpdate;

	private TestCrud<PocPipeline, String, PocPipeline> testCrud;

	@Before
	public void before() {
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
				return pocPipelineRepository.save(obj);
			}

			@Override
			protected void validSave(PocPipeline objSave, PocPipeline objOrigin) {
				assertNotNull(objSave.getId());
				assertNotNull(objOrigin.getCadastro());
				assertNotNull(objOrigin.getUltimaAlteracao());
			}

			@Override
			protected PocPipeline find(String id, PocPipeline obj, PocPipeline objSave) {
				return pocPipelineRepository.findById(id).get();
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
				return pocPipelineRepository.findAll();
			}

			@Override
			protected boolean getFindAll(PocPipeline objList, PocPipeline objFind, PocPipeline objSave,
					PocPipeline objOrigin) {
				return objList.getId().equals(objSave.getId());
			}

			@Override
			protected PocPipeline prepareUpdate(PocPipeline objFind, PocPipeline objSave, PocPipeline objOrigin) {
				return objSave.update(pocPipelineUpdate);
			}

			@Override
			protected void update(PocPipeline objUpdate, PocPipeline objFind, PocPipeline objSave,
					PocPipeline objOrigin) {
				pocPipelineRepository.save(objUpdate);
			}

			@Override
			protected void validUpdate(PocPipeline objFindUpdate, PocPipeline objUpdate, PocPipeline objFind,
					PocPipeline objSave, PocPipeline objOrigin) {
				assertEquals(objOrigin.getId(), objFindUpdate.getId());
				assertEquals(pocPipelineUpdate.getDescricao(), objFindUpdate.getDescricao());
				assertEquals(objFind.getCadastro(), objFindUpdate.getCadastro());
			}

			@Override
			protected void delete(String id, PocPipeline objFindUpdate) {
				pocPipelineRepository.delete(objFindUpdate);
				exceptionRule.expect(NoSuchElementException.class);
			}

		};
	}

	@Test
	public void testCrud() {
		testCrud.run(this.pocPipeline);
	}

}
