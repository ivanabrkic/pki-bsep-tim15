package tim15.pki.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tim15.pki.model.Extension;

@Repository
public interface ExtensionRepository extends JpaRepository<Extension, Long> {
    Extension findByName(String name);
}
