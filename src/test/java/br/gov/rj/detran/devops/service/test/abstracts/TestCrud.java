package br.gov.rj.detran.devops.service.test.abstracts;

import java.util.Collection;

/**
 * Testar um crud e todas as suas etapas
 * 
 * @author redhat
 *
 * @param <T> Classe da Entidade
 * @param <K> Classe da chave da Entidade
 * @param <S> Classe do retorno ao incluir Entidade
 */
public abstract class TestCrud<T, K, S> {

	/**
	 * Gerar id após salvar
	 * 
	 * @param objSave objeto salvo
	 * @param objOrigin objeto original
	 * @return K
	 */
	protected abstract K id(S objSave, T objOrigin);

	/**
	 * Salvar entidade e retornar
	 * 
	 * @param obj objeto original
	 * @return S
	 */
	protected abstract S save(T obj);

	/**
	 * Procurar entidade pelo id
	 * 
	 * @param id id do objeto salvo
	 * @param obj objeto original
	 * @param objSave objeto salvo
	 * @return T
	 */
	protected abstract T find(K id, T obj, S objSave);

	/**
	 * Pesquisa da entidade
	 * 
	 * @param id id do objeto salvo
	 * @param obj objeto original
	 * @param objSave objeto salvo
	 * @return Collection
	 */
	protected abstract Collection<T> findAll(K id, T obj, S objSave);

	/**
	 * Verifica a entidade salva após a pesquisa
	 * 
	 * @param objList obejto da lista pesquisada
	 * @param objFind objeto encontrado após salvar
	 * @param objSave obejto salvo
	 * @param objOrigin objeto original
	 * @return boolean
	 */
	protected abstract boolean getFindAll(T objList, T objFind, S objSave, T objOrigin);

	/**
	 * Valida após salvar
	 * 
	 * @param objSave objeto salvo
	 * @param objOrigin objeto original
	 */
	protected abstract void validSave(S objSave, T objOrigin);

	/**
	 * Valida após buscar pela chave
	 * 
	 * @param objFind objeto encontrado após salvar 
	 * @param objSave objeto salvo
	 * @param objOrigin objeto original
	 */
	protected abstract void validFind(T objFind, S objSave, T objOrigin);

	/**
	 * Prepara para alteração
	 * 
	 * @param objFind objeto encontrado após salvar
	 * @param objSave objeto salvo
	 * @param objOrigin objeto original
	 * @return T
	 */
	protected abstract T prepareUpdate(T objFind, S objSave, T objOrigin);

	/**
	 * Alteração
	 * 
	 * @param objUpdate objeto alterado
	 * @param objFind objeto encontrado após salvar
	 * @param objSave objeto salvo
	 * @param objOrigin objeto original
	 */
	protected abstract void update(T objUpdate, T objFind, S objSave, T objOrigin);

	/**
	 * Valida após buscar registro após alteração
	 * 
	 * @param objFindUpdate objeto encontrado após alterar
	 * @param objUpdate objeto alterado
	 * @param objFind objeto encontrado após salvar
	 * @param objSave objeto salvo
	 * @param objOrigin objeto original
	 */
	protected abstract void validUpdate(T objFindUpdate, T objUpdate, T objFind, S objSave, T objOrigin);

	/**
	 * Exclusão
	 * 
	 * @param id  id do objeto salvo
	 * @param objFindUpdate objeto encontrado após alterar
	 */
	protected abstract void delete(K id, T objFindUpdate);

	/**
	 * Teste e valida as operações como um fluxo do crud:<br>
	 * <br>
	 * <b>inclusão</b><br>
	 * <b>buscar pela chave</b><br>
	 * <b>buscar todos</b><br>
	 * <b>alterar</b><br>
	 * <b>excluir</b><br>
	 * <b>verificar se exclui</b><br>
	 * 
	 * @param obj entidade inicial para o fluxo
	 */
	public void run(T obj) {
		S objSave = this.save(obj);
		this.validSave(objSave, obj);
		K id = this.id(objSave, obj);
		T objFind = this.find(id, obj, objSave);
		this.validFind(objFind, objSave, obj);
		Collection<T> objs = this.findAll(id, objFind, objSave);
		objs.stream().filter(objList -> getFindAll(objList, objFind, objSave, obj)).findFirst().orElseThrow();
		T objUpdate = this.prepareUpdate(objFind, objSave, obj);
		this.update(objUpdate, objFind, objSave, obj);
		T objFindUpdate = this.find(id, obj, objSave);
		this.validUpdate(objFindUpdate, objUpdate, objFind, objSave, obj);
		this.delete(id, objFindUpdate);
		T tt = this.find(id, obj, objSave);
		System.out.println(tt);
	}

}
