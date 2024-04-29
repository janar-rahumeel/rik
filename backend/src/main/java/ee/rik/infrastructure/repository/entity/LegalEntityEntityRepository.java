package ee.rik.infrastructure.repository.entity;

import java.util.Optional;

import ee.rik.infrastructure.entity.LegalEntityEntity;

import org.springframework.data.repository.CrudRepository;

public interface LegalEntityEntityRepository extends CrudRepository<LegalEntityEntity, Long> {

    Optional<LegalEntityEntity> findByRegistrationCode(String registrationCode);

}
