package eaismart.webapps.sigpc.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import eaismart.webapps.sigpc.entity.Movement;
import eaismart.webapps.sigpc.util.constants.Month;
import eaismart.webapps.sigpc.util.constants.Status;
import eaismart.webapps.sigpc.util.constants.TransitionType;

/**
 * @author Iekiny Marcel Feb 17, 2021
 */
@Repository
public interface IMovementRepository extends JpaRepository<Movement, Long>, JpaSpecificationExecutor<Movement> {

    public List<Movement> findByType(TransitionType type);

    public List<Movement> findByMonth(Month month);

    public List<Movement> findByStatus(Status status);

    static Specification<Movement> hasCode(String code) {
        return (root, query, cb) -> cb.equal(root.get("numDoc"), code);
    }

    static Specification<Movement> hasDateBetween(Date beginDate, Date endDate) {
        return (root, query, cb) -> cb.between(root.get("date"), beginDate, endDate);
    }

    static Specification<Movement> hasType(TransitionType type) {
        return (root, query, cb) -> cb.equal(root.get("type"), type);
    }

}
