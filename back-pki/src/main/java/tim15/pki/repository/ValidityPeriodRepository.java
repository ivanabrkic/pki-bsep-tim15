package tim15.pki.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tim15.pki.model.Certificate;
import tim15.pki.model.ValidityPeriod;


@Repository
public interface ValidityPeriodRepository extends JpaRepository<ValidityPeriod, Long> {
    ValidityPeriod findByCertificate(Certificate c);

    //  EXAMPLES OF PARAMETERIZED QUERY
    @Query("select vp from ValidityPeriod vp where vp.certificate = ?1")
    ValidityPeriod getValidityPeriodCertificate(Certificate c);
}
