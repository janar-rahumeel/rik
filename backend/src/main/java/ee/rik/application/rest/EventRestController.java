package ee.rik.application.rest;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Set;

import jakarta.validation.Valid;

import ee.rik.application.request.AddLegalEntityParticipantRequest;
import ee.rik.application.request.AddPersonParticipantRequest;
import ee.rik.application.request.CreateEventRequest;
import ee.rik.application.request.EventPayload;
import ee.rik.application.request.ListEventsRequest;
import ee.rik.application.request.ModifyEventRequest;
import ee.rik.application.response.EventParticipantsResponse;
import ee.rik.application.response.LegalEntityParticipantResponse;
import ee.rik.application.response.ListEventsResponse;
import ee.rik.application.response.PersonParticipantResponse;
import ee.rik.domain.EntityFieldErrorCodeConstant;
import ee.rik.domain.EntityFieldNotValidException;
import ee.rik.domain.Event;
import ee.rik.domain.EventListItem;
import ee.rik.domain.EventParticipant;
import ee.rik.domain.LegalEntityParticipant;
import ee.rik.domain.PersonParticipant;
import ee.rik.domain.service.EventService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventRestController {

    private final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    private final EventService eventService;

    @PostMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ListEventsResponse> listAll(@Valid @RequestBody ListEventsRequest listEventsRequest) {
        Set<EventListItem> events = eventService.getAllEvents(listEventsRequest.getNewEvents());
        return ResponseEntity.ok(ListEventsResponse.builder().events(events).build());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createEvent(@Valid @RequestBody CreateEventRequest createEventRequest) {
        EventPayload eventPayload = createEventRequest.getEvent();
        LocalDateTime startDateTime = toLocalDateTime(eventPayload.getStartDateTime());
        Event event = toEvent(eventPayload, startDateTime);
        Long id = eventService.createEvent(event);
        return ResponseEntity.created(URI.create("/events/" + id)).build();
    }

    public static LocalDateTime toLocalDateTime(String startDateTimeText) {
        try {
            return LocalDateTime.parse(startDateTimeText, DATE_TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new EntityFieldNotValidException(
                    "event.startDateTime",
                    EntityFieldErrorCodeConstant.Event.START_DATE_TIME_INVALID_FORMAT);
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EventPayload> getEvent(@PathVariable Long id) {
        Event event = eventService.getEvent(id);
        EventPayload eventPayload = toPayload(event);
        return ResponseEntity.ok(eventPayload);
    }

    private static EventPayload toPayload(Event event) {
        String startDateTime = event.getStartDateTime().format(DATE_TIME_FORMATTER);
        return EventPayload.builder()
                .name(event.getName())
                .startDateTime(startDateTime)
                .location(event.getLocation())
                .description(event.getDescription())
                .build();
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void modifyEvent(@PathVariable Long id, @Valid @RequestBody ModifyEventRequest modifyEventRequest) {
        EventPayload eventPayload = modifyEventRequest.getEvent();
        LocalDateTime startDateTime = toLocalDateTime(eventPayload.getStartDateTime());
        Event event = toEvent(eventPayload, startDateTime);
        eventService.modifyEvent(id, event);
    }

    private static Event toEvent(EventPayload eventPayload, LocalDateTime startDateTime) {
        return Event.builder()
                .name(eventPayload.getName())
                .startDateTime(startDateTime)
                .location(eventPayload.getLocation())
                .description(eventPayload.getDescription())
                .build();
    }

    @DeleteMapping(value = "/{id}")
    public void deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
    }

    @GetMapping(value = "/{id}/participants", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EventParticipantsResponse> getParticipants(@PathVariable Long id) {
        Set<EventParticipant> participants = eventService.listAllParticipants(id);
        return ResponseEntity.ok(EventParticipantsResponse.builder().participants(participants).build());
    }

    @PostMapping(
            value = "/{id}/participants/person",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PersonParticipantResponse> addPersonParticipant(
            @PathVariable Long id,
            @Valid @RequestBody AddPersonParticipantRequest addPersonParticipantRequest) {
        PersonParticipant personParticipant = eventService
                .addPersonParticipant(id, addPersonParticipantRequest.getPersonParticipant());
        return ResponseEntity.ok(PersonParticipantResponse.builder().personParticipant(personParticipant).build());
    }

    @DeleteMapping(value = "/{id}/participants/person/{personParticipantId}")
    public void removePersonParticipant(@PathVariable Long id, @PathVariable Long personParticipantId) {
        eventService.removePersonParticipant(id, personParticipantId);
    }

    @PostMapping(
            value = "/{id}/participants/legal-entity",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LegalEntityParticipantResponse> addLegalEntityParticipant(
            @PathVariable Long id,
            @Valid @RequestBody AddLegalEntityParticipantRequest addLegalEntityParticipantRequest) {
        LegalEntityParticipant legalEntityParticipant = eventService
                .addLegalEntityParticipant(id, addLegalEntityParticipantRequest.getLegalEntityParticipant());
        return ResponseEntity
                .ok(LegalEntityParticipantResponse.builder().legalEntityParticipant(legalEntityParticipant).build());
    }

    @DeleteMapping(value = "/{id}/participants/legal-entity/{legalEntityParticipantId}")
    public void removeLegalEntityParticipant(@PathVariable Long id, @PathVariable Long legalEntityParticipantId) {
        eventService.removeLegalEntityParticipant(id, legalEntityParticipantId);
    }

}
