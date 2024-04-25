package ee.rik.infrastructure.repository;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ee.rik.domain.EventParticipant;
import ee.rik.domain.repository.EventParticipantRepository;
import ee.rik.infrastructure.entity.EventParticipantEntity;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EventParticipantRepositoryImpl implements EventParticipantRepository {

    private final JdbcTemplate jdbcTemplate;
    private final EventParticipantEntityRepository eventParticipantEntityRepository;

    @Override
    public Set<EventParticipant> listAll(Long eventId) {
        try (Stream<EventParticipantEntity> stream = eventParticipantEntityRepository
                .streamAllByEventIdOrderByNameAsc(eventId)) {
            return stream.map(EventParticipantRepositoryImpl::map).collect(Collectors.toCollection(LinkedHashSet::new));
        }
    }

    private static EventParticipant map(EventParticipantEntity eventParticipantEntity) {
        return EventParticipant.builder()
                .id(eventParticipantEntity.getId())
                .name(eventParticipantEntity.getName())
                .identityCode(eventParticipantEntity.getIdentityCode())
                .build();
    }

    @Override
    public boolean personParticipantExists(Long eventId, Long personParticipantId) {
        return jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM EVENT_PERSON_PARTICIPANT_X WHERE EVENT_ID = ? AND PERSON_PARTICIPANT_ID = ?",
                new Object[] { eventId, personParticipantId },
                Integer.class) == 1;
    }

    @Override
    @Transactional
    public void addPersonParticipant(Long eventId, Long personParticipantId) {
        jdbcTemplate.update(
                "INSERT INTO EVENT_PERSON_PARTICIPANT_X (EVENT_ID, PERSON_PARTICIPANT_ID) VALUES (?, ?)",
                eventId,
                personParticipantId);
    }

    @Override
    public boolean legalEntityParticipantExists(Long eventId, Long legalEntityParticipantId) {
        return jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM EVENT_LEGAL_ENTITY_PARTICIPANT_X WHERE EVENT_ID = ? AND LEGAL_ENTITY_PARTICIPANT_ID = ?",
                new Object[] { eventId, legalEntityParticipantId },
                Integer.class) == 1;
    }

    @Override
    @Transactional
    public void addLegalEntityParticipant(Long eventId, Long legalEntityParticipantId) {
        jdbcTemplate.update(
                "INSERT INTO EVENT_LEGAL_ENTITY_PARTICIPANT_X (EVENT_ID, LEGAL_ENTITY_PARTICIPANT_ID) VALUES (?, ?)",
                eventId,
                legalEntityParticipantId);
    }

}
