package ee.rik.infrastructure.repository;

import ee.rik.infrastructure.entity.PersonParticipantEntity;
import org.springframework.data.repository.CrudRepository;

public interface PersonParticipantEntityRepository extends CrudRepository<PersonParticipantEntity, Long> {
}
