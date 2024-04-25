package ee.rik.infrastructure.repository;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import ee.rik.infrastructure.entity.EventEntity;

import org.springframework.data.repository.CrudRepository;

public interface EventEntityRepository extends CrudRepository<EventEntity, Long> {

    Stream<EventEntity> streamAllByStartDateTimeBeforeOrderByStartDateTimeAsc(LocalDateTime startDateTime);

}
