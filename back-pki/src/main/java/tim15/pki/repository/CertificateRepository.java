package tim15.pki.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;
import tim15.pki.model.Certificate;
import tim15.pki.model.enums.CertificateStatus;

import java.util.List;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate, Long> {
    
    List<Certificate> findAll();

    Certificate findOneBySerialNumber(String serialNumber);

    Certificate save(Certificate certificate);

    void removeBySerialNumber(String serialNumber);

    List<Certificate> findByIsCAAndIsActiveAndCertificateStatus(boolean ca, boolean active, CertificateStatus status);

    @Query("select c from Certificate c where c.revokeReason is not null")
    List<Certificate> findRevokedCertificates();

    Certificate getOneBySerialNumber(String serialNumber);
}
