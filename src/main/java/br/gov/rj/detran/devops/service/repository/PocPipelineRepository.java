package br.gov.rj.detran.devops.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import br.gov.rj.detran.devops.service.domain.PocPipeline;

/**
 * Metodos para gerenciamento do repositorio da entidade JpaRepository
 * @author redhat
 *
 */
@Repository
public interface PocPipelineRepository extends JpaRepository<PocPipeline, String> {
}
