package ee.rik.infrastructure.repository;

import ee.rik.infrastructure.entity.LegalEntityParticipantEntity;
import org.springframework.data.repository.CrudRepository;

public interface LegalEntityParticipantEntityRepository extends CrudRepository<LegalEntityParticipantEntity, Long> {
}
