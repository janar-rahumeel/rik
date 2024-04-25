package ee.rik.infrastructure.repository;

import java.util.stream.Stream;

import ee.rik.infrastructure.entity.EventParticipantEntity;

import org.springframework.data.repository.Repository;

public interface EventParticipantEntityRepository extends Repository<EventParticipantEntity, String> {

    Stream<EventParticipantEntity> streamAllByEventIdOrderByNameAsc(Long eventId);

}
