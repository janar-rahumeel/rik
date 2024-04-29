package ee.rik.infrastructure.repository;

import java.util.List;
import java.util.stream.Stream;

import ee.rik.domain.EventParticipant;
import ee.rik.domain.repository.EventParticipantRepository;
import ee.rik.infrastructure.entity.EventParticipantEntity;
import ee.rik.infrastructure.repository.entity.EventParticipantEntityRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EventParticipantRepositoryImpl implements EventParticipantRepository {

    private final EventParticipantEntityRepository eventParticipantEntityRepository;

    @Override
    public List<EventParticipant> listAll(Long eventId) {
        try (Stream<EventParticipantEntity> stream = eventParticipantEntityRepository
                .streamAllByEventIdOrderByNameAsc(eventId)) {
            return stream.map(EventParticipantRepositoryImpl::map).toList();
        }
    }

    private static EventParticipant map(EventParticipantEntity eventParticipantEntity) {
        return EventParticipant.builder()
                .id(eventParticipantEntity.getId())
                .name(eventParticipantEntity.getName())
                .identityCode(eventParticipantEntity.getIdentityCode())
                .build();
    }

}
