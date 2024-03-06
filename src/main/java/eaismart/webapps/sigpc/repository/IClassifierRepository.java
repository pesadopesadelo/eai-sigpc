package eaismart.webapps.sigpc.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import eaismart.webapps.sigpc.entity.Classifier;
import eaismart.webapps.sigpc.util.constants.Status;
import java.util.List;

/**
 * @author Iekiny Marcel Feb 17, 2021
 */
public interface IClassifierRepository extends JpaRepository<Classifier, Long> {

    public Optional<Classifier> findByCode(String code);

    public List<Classifier> findByStatus(Status status);

}
