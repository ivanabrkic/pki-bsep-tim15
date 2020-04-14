package tim15.pki.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tim15.pki.model.SystemEntity;

@Repository
public interface SystemEntityRepository extends JpaRepository<SystemEntity, Long> {
    SystemEntity findByUid(String valueOf);
}
