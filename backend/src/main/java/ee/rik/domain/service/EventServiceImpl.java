package ee.rik.domain.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import ee.rik.domain.EntityFieldErrorCodeConstant;
import ee.rik.domain.EntityFieldNotValidException;
import ee.rik.domain.Event;
import ee.rik.domain.EventListItem;
import ee.rik.domain.EventParticipant;
import ee.rik.domain.LegalEntityParticipant;
import ee.rik.domain.PersonParticipant;
import ee.rik.domain.repository.EventParticipantRepository;
import ee.rik.domain.repository.EventPersonParticipantRepository;
import ee.rik.domain.repository.EventRepository;
import ee.rik.domain.repository.LegalEntityParticipantRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final PersonParticipantService personParticipantService;
    private final EventRepository eventRepository;
    private final EventParticipantRepository eventParticipantRepository;
    private final EventPersonParticipantRepository eventPersonParticipantRepository;
    private final LegalEntityParticipantRepository legalEntityParticipantRepository;

    @Override
    public Set<EventListItem> getAllEvents(Boolean newEvents) {
        if (newEvents) {
            return eventRepository.getAllUntil(LocalDateTime.now().plusYears(1));
        }
        return eventRepository.getAllUntil(LocalDateTime.now());
    }

    @Override
    public Event getEvent(Long id) {
        return eventRepository.get(id);
    }

    @Override
    public Long createEvent(Event event) {
        if (event.getStartDateTime().isBefore(LocalDateTime.now())) {
            throw new EntityFieldNotValidException(
                    "event.startDateTime",
                    EntityFieldErrorCodeConstant.Event.START_DATE_TIME_MUST_BE_IN_THE_FUTURE);
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
            throw new EntityFieldNotValidException(
                    "event.startDateTime",
                    EntityFieldErrorCodeConstant.Event.START_DATE_TIME_MUST_BE_IN_THE_FUTURE);
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

    @Override
    @Transactional
    public PersonParticipant addPersonParticipant(Long id, PersonParticipant personParticipant) {
        if (eventPersonParticipantRepository
                .existsByEventIdAndNationalIdentificationCode(id, personParticipant.getNationalIdentificationCode())) {
            throw new EntityFieldNotValidException(
                    "personParticipant.general",
                    EntityFieldErrorCodeConstant.EventParticipant.PERSON_ALREADY_ADDED);
        }
        return personParticipantService.createPersonParticipant(id, personParticipant);
    }

    @Override
    @Transactional
    public LegalEntityParticipant addLegalEntityParticipant(Long id, LegalEntityParticipant legalEntityParticipant) {
        Optional<Pair<Long, LegalEntityParticipant>> optionalPair = legalEntityParticipantRepository
                .findByRegistrationCode(legalEntityParticipant.getRegistrationCode());
        if (optionalPair.isPresent()) {
            Pair<Long, LegalEntityParticipant> existingPair = optionalPair.get();
            if (eventParticipantRepository.legalEntityParticipantExists(id, existingPair.getFirst())) {
                throw new EntityFieldNotValidException(
                        "legalEntityParticipant.general",
                        EntityFieldErrorCodeConstant.EventParticipant.LEGAL_ENTITY_ALREADY_ADDED);
            }
            eventParticipantRepository.addLegalEntityParticipant(id, existingPair.getFirst());
            return existingPair.getSecond();
        }
        Long legalEntityParticipantId = legalEntityParticipantRepository.create(legalEntityParticipant);
        eventParticipantRepository.addLegalEntityParticipant(id, legalEntityParticipantId);
        return legalEntityParticipantRepository.get(legalEntityParticipantId);
    }

    @Override
    public void removeLegalEntityParticipant(Long id, Long legalEntityParticipantId) {
        eventParticipantRepository.removeLegalEntityParticipant(id, legalEntityParticipantId);
    }

}
