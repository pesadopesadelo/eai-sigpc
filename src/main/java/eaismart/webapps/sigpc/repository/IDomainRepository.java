package eaismart.webapps.sigpc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eaismart.webapps.sigpc.entity.Domain;
import eaismart.webapps.sigpc.util.constants.Status;

/**
 * @author Iekiny Marcel
 * Dec 2, 2020
 */

@Repository("sigpcDomainRepository")
public interface IDomainRepository extends JpaRepository<Domain, Long>{
	
	public Domain findByDominioAndCodigoAndEstado(String dominio, String codigo, Status estado);
	public Domain findByDominioAndCodigo(String dominio, String codigo);
	public List<Domain> findByDominioAndEstado(String dominio, Status estado);
	public List<Domain> findByDominio(String dominio);
	
}
