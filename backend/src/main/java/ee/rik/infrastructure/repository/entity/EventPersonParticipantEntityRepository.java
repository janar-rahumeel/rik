package ee.rik.infrastructure.repository.entity;

import ee.rik.infrastructure.entity.EventPersonParticipantEntity;

import org.springframework.data.repository.CrudRepository;

public interface EventPersonParticipantEntityRepository extends CrudRepository<EventPersonParticipantEntity, Long> {

    boolean existsByEventIdAndPersonNationalIdentificationCode(Long eventId, String personNationalIdentificationCode);

}
