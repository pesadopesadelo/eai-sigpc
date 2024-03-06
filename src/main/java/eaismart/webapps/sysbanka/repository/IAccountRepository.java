package eaismart.webapps.sysbanka.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eaismart.webapps.sysbanka.entity.Account;

/**
 * @author Iekiny Marcel
 * Feb 16, 2021
 */
@Repository
public interface IAccountRepository extends JpaRepository<Account, Long>{

}
