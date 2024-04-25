package ee.rik.application.rest;

import java.net.URI;
import java.util.Set;

import jakarta.validation.Valid;

import ee.rik.application.request.AddLegalEntityParticipantRequest;
import ee.rik.application.request.AddPersonParticipantRequest;
import ee.rik.application.request.CreateEventRequest;
import ee.rik.application.request.ListEventsRequest;
import ee.rik.application.request.ModifyEventRequest;
import ee.rik.application.response.EventParticipantsResponse;
import ee.rik.application.response.LegalEntityParticipantResponse;
import ee.rik.application.response.ListEventsResponse;
import ee.rik.application.response.PersonParticipantResponse;
import ee.rik.domain.EventParticipant;
import ee.rik.domain.LegalEntityParticipant;
import ee.rik.domain.ListEvent;
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

    private final EventService eventService;

    @PostMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ListEventsResponse> listAll(@Valid @RequestBody ListEventsRequest listEventsRequest) {
        Set<ListEvent> events = eventService.listAll(listEventsRequest.getNewEvents());
        return ResponseEntity.ok(ListEventsResponse.builder().events(events).build());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createEvent(@Valid @RequestBody CreateEventRequest createEventRequest) {
        Long id = eventService.createEvent(createEventRequest.getEvent());
        return ResponseEntity.created(URI.create("/events/" + id)).build();
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void modifyEvent(@PathVariable Long id, @Valid @RequestBody ModifyEventRequest modifyEventRequest) {
        eventService.modifyEvent(id, modifyEventRequest.getEvent());
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

}
