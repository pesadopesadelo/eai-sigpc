package eaismart.webapps.sysbanka.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eaismart.webapps.sysbanka.entity.Domain;
import eaismart.webapps.sysbanka.util.constants.Status;
/**
 * @author Iekiny Marcel
 * Dec 2, 2020
 */

@Repository("sysbankaDomainRepository")
public interface IDomainRepository extends JpaRepository<Domain, Long>{
	
	public Domain findByDominioAndCodigoAndEstado(String dominio, String codigo, Status estado);
	public List<Domain> findByDominioAndEstado(String dominio, Status estado);
	
}
