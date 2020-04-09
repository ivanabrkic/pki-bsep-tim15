package tim15.pki.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tim15.pki.model.Certificate;
import java.util.List;

//FALI extends JpaRepository<Certificate, Long>
//U KRAJNJEM SLUCAJU TREBA GA CELOG IMPLEMENTIRATI
public interface CertificateRepository extends JpaRepository<Certificate, Long> {

    List<Certificate> findAll();

    Certificate findOneBySerialNumber(String serialNumber);

    Certificate save(Certificate certificate);

    void removeBySerialNumber(String serialNumber);

    List<String> findByIsCA(boolean b);
}
