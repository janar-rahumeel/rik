package ee.rik.infrastructure.repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jakarta.persistence.EntityNotFoundException;

import ee.rik.domain.Event;
import ee.rik.domain.EventListItem;
import ee.rik.domain.repository.EventRepository;
import ee.rik.infrastructure.entity.EventEntity;
import ee.rik.infrastructure.entity.LegalEntityParticipantEntity;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EventRepositoryImpl implements EventRepository {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    private final EventEntityRepository eventEntityRepository;

    @Override
    public Set<EventListItem> getAllUntil(LocalDateTime localDateTime) {
        try (Stream<EventEntity> stream = eventEntityRepository
                .streamAllByStartDateTimeBeforeOrderByStartDateTimeAsc(localDateTime)) {
            return stream.map(EventRepositoryImpl::toListItem).collect(Collectors.toCollection(LinkedHashSet::new));
        }
    }

    private static EventListItem toListItem(EventEntity eventEntity) {
        String startDate = DATE_FORMATTER.format(eventEntity.getStartDateTime());
        Integer totalParticipantCount = eventEntity.getEventPersonParticipantEntities().size()
                + eventEntity.getLegalEntityParticipantEntities()
                        .stream()
                        .map(LegalEntityParticipantEntity::getParticipantCount)
                        .reduce(0, Integer::sum);
        return EventListItem.builder()
                .id(eventEntity.getId())
                .name(eventEntity.getName())
                .startDate(startDate)
                .location(eventEntity.getLocation())
                .totalParticipantCount(totalParticipantCount)
                .build();
    }

    @Override
    public Event get(Long id) {
        EventEntity eventEntity = eventEntityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No EventEntity found: " + id));
        return Event.builder()
                .name(eventEntity.getName())
                .startDateTime(eventEntity.getStartDateTime())
                .location(eventEntity.getLocation())
                .description(eventEntity.getDescription())
                .build();
    }

    @Override
    @Transactional
    public Long create(Event event) {
        EventEntity eventEntity = toEntity(event);
        return eventEntityRepository.save(eventEntity).getId();
    }

    private static EventEntity toEntity(Event event) {
        return EventEntity.builder()
                .name(event.getName())
                .startDateTime(event.getStartDateTime())
                .location(event.getLocation())
                .description(event.getDescription())
                .build();
    }

    @Override
    @Transactional
    public void modify(Long id, Event event) {
        EventEntity eventEntity = eventEntityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No EventEntity found: " + id));
        if (!eventEntity.getName().equals(event.getName())) {
            eventEntity.setName(event.getName());
        }
        if (!eventEntity.getStartDateTime().equals(event.getStartDateTime())) {
            eventEntity.setStartDateTime(event.getStartDateTime());
        }
        if (!eventEntity.getLocation().equals(event.getLocation())) {
            eventEntity.setLocation(event.getLocation());
        }
        if (!eventEntity.getDescription().equals(event.getDescription())) {
            eventEntity.setDescription(event.getDescription());
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        eventEntityRepository.deleteById(id);
    }

}
