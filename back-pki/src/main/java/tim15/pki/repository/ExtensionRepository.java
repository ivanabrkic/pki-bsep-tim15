package tim15.pki.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tim15.pki.model.Extension;

public interface ExtensionRepository extends JpaRepository<Extension, Long> {
}
