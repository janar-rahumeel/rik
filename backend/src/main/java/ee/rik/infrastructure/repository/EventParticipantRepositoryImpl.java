package ee.rik.infrastructure.repository;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

import ee.rik.domain.EventParticipant;
import ee.rik.domain.repository.EventParticipantRepository;
import ee.rik.infrastructure.entity.EventParticipantEntity;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EventParticipantRepositoryImpl implements EventParticipantRepository {

    private final EventParticipantEntityRepository eventParticipantEntityRepository;

    @Override
    public Set<EventParticipant> listAll(Long eventId) {
        return eventParticipantEntityRepository.streamAllByEventIdOrderByNameAsc(eventId)
                .map(EventParticipantRepositoryImpl::map)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private static EventParticipant map(EventParticipantEntity eventParticipantEntity) {
        return EventParticipant.builder()
                .id(eventParticipantEntity.getId())
                .name(eventParticipantEntity.getName())
                .identityCode(eventParticipantEntity.getIdentityCode())
                .build();
    }

}
