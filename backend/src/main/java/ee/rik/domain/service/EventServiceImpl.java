package ee.rik.domain.service;

import ee.rik.domain.EntityFieldErrorCodeConstant;
import ee.rik.domain.EntityFieldNotValidException;
import ee.rik.domain.Event;
import ee.rik.domain.EventParticipant;
import ee.rik.domain.repository.EventParticipantRepository;
import ee.rik.domain.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final EventParticipantRepository eventParticipantRepository;

    @Override
    public Long createEvent(Event event) {
        if (event.getStartDateTime().isBefore(LocalDateTime.now())) {
            throw new EntityFieldNotValidException("event.startDateTime", EntityFieldErrorCodeConstant.Event.START_DATE_TIME_MUST_BE_IN_THE_FUTURE);
        }
        return eventRepository.create(event);
    }

    @Override
    public void modifyEvent(Long id, Event event) {
        Event eventToModify = eventRepository.get(id);
        LocalDateTime startDateTime = eventToModify.getStartDateTime();
        if (startDateTime.isBefore(LocalDateTime.now())) {
            throw new EntityFieldNotValidException("event.general", EntityFieldErrorCodeConstant.Event.NOT_ALLOWED_TO_CHANGE);
        }
        if (event.getStartDateTime().isBefore(LocalDateTime.now())) {
            throw new EntityFieldNotValidException("event.startDateTime", EntityFieldErrorCodeConstant.Event.START_DATE_TIME_MUST_BE_IN_THE_FUTURE);
        }
        eventRepository.modify(id, event);
    }

    @Override
    public void deleteEvent(Long id) {
        LocalDateTime startDateTime = eventRepository.get(id).getStartDateTime();
        if (startDateTime.isBefore(LocalDateTime.now())) {
            throw new EntityFieldNotValidException("event.general", EntityFieldErrorCodeConstant.Event.NOT_ALLOWED_TO_DELETE);
        }
        eventRepository.delete(id);
    }

    @Override
    public Set<EventParticipant> listAllParticipants(Long id) {
        return eventParticipantRepository.listAll(id);
    }

}
