package ee.rik.infrastructure.repository.entity;

import ee.rik.infrastructure.entity.EventLegalEntityParticipantEntity;

import org.springframework.data.repository.CrudRepository;

public interface EventLegalEntityParticipantEntityRepository extends CrudRepository<EventLegalEntityParticipantEntity, Long> {

    boolean existsByEventIdAndLegalEntityRegistrationCode(Long eventId, String registrationCode);

}
