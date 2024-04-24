package ee.rik.infrastructure.repository;

import ee.rik.infrastructure.entity.EventEntity;
import org.springframework.data.repository.CrudRepository;

public interface EventEntityRepository extends CrudRepository<EventEntity, Long> {
}
