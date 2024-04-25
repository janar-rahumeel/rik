package ee.rik.infrastructure.repository;

import java.util.Optional;

import ee.rik.infrastructure.entity.LegalEntityParticipantEntity;

import org.springframework.data.repository.CrudRepository;

public interface LegalEntityParticipantEntityRepository extends CrudRepository<LegalEntityParticipantEntity, Long> {

    Optional<LegalEntityParticipantEntity> findByRegistrationCode(String registrationCode);

    void flush();

}
