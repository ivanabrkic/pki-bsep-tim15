package tim15.pki.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tim15.pki.model.Certificate;
import tim15.pki.model.ValidityPeriod;

@Repository
public interface ValidityPeriodRepository extends JpaRepository<ValidityPeriod, Long> {
    ValidityPeriod findByCertificate(Certificate c);
}
