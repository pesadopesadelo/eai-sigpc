package eaismart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eaismart.entity.Application;
import java.util.List;
import java.util.Optional;

/**
 * @author Iekiny Marcel Dec 2, 2020
 */

@Repository
public interface IApplicationRepository extends JpaRepository<Application, Long> {

    Optional<List<Application>> findByCode(String code);

}
