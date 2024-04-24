package ee.rik.infrastructure.repository;

import ee.rik.domain.Event;
import ee.rik.domain.repository.EventRepository;
import ee.rik.infrastructure.entity.EventEntity;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EventRepositoryImpl implements EventRepository {

    private final EventEntityRepository eventEntityRepository;

    @Override
    public Event get(Long id) {
        EventEntity eventEntity = eventEntityRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No event found: " + id));
        return Event.builder().name(eventEntity.getName()).startDateTime(eventEntity.getStartDateTime()).location(eventEntity.getLocation()).description(eventEntity.getDescription()).build();
    }

    @Override
    @Transactional
    public Long create(Event event) {
        EventEntity eventEntity = EventEntity.of(event);
        return eventEntityRepository.save(eventEntity).getId();
    }

    @Override
    @Transactional
    public void modify(Long id, Event event) {
        EventEntity eventEntity = eventEntityRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No event found: " + id));
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
