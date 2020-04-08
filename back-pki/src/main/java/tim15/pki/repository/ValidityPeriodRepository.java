package tim15.pki.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tim15.pki.model.Certificate;

public interface ValidityPeriodRepository extends JpaRepository<ValidityPeriodRepository, Long> {
}
