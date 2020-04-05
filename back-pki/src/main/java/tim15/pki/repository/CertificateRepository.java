package tim15.pki.repository;

import tim15.pki.model.Certificate;
import java.util.List;

//FALI extends JpaRepository<Certificate, Long>
//U KRAJNJEM SLUCAJU TREBA GA CELOG IMPLEMENTIRATI
public interface CertificateRepository {
    List<Certificate> findAll();
    Certificate findOneBySerialNumber(String serialNumber);
    List<Certificate> findAllByName(String name);
    Certificate save(Certificate certificate);
    void removeBySerialNumber(String serialNumber);

    //@Query("select c from Certificate c where c.revokeReason != null")
    //PROVERITI upit!!
    List<Certificate> findRevokedCertificates();
}
