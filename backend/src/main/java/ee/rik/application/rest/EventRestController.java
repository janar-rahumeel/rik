package ee.rik.application.rest;

import java.net.URI;
import java.util.List;

import jakarta.validation.Valid;

import ee.rik.application.mapper.EventMapper;
import ee.rik.application.model.EventData;
import ee.rik.application.request.CreateEventRequest;
import ee.rik.application.request.ListEventsRequest;
import ee.rik.application.request.ModifyEventRequest;
import ee.rik.application.response.EventParticipantsResponse;
import ee.rik.application.response.ListEventsResponse;
import ee.rik.domain.Event;
import ee.rik.domain.EventListItem;
import ee.rik.domain.EventParticipant;
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

    private final EventMapper eventMapper;
    private final EventService eventService;

    @PostMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ListEventsResponse> listAll(@Valid @RequestBody ListEventsRequest listEventsRequest) {
        List<EventListItem> events = eventService.getAllEvents(listEventsRequest.getNewEvents());
        return ResponseEntity.ok(ListEventsResponse.builder().events(events).build());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createEvent(@Valid @RequestBody CreateEventRequest createEventRequest) {
        EventData eventData = createEventRequest.getEvent();
        Event event = eventMapper.map(eventData);
        Long id = eventService.createEvent(event);
        return ResponseEntity.created(URI.create("/events/" + id)).build();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EventData> getEvent(@PathVariable Long id) {
        Event event = eventService.getEvent(id);
        EventData eventData = eventMapper.map(event);
        return ResponseEntity.ok(eventData);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void modifyEvent(@PathVariable Long id, @Valid @RequestBody ModifyEventRequest modifyEventRequest) {
        EventData eventData = modifyEventRequest.getEvent();
        Event event = eventMapper.map(eventData);
        eventService.modifyEvent(id, event);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
    }

    @GetMapping(value = "/{id}/participants", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EventParticipantsResponse> getParticipants(@PathVariable Long id) {
        List<EventParticipant> participants = eventService.listAllParticipants(id);
        return ResponseEntity.ok(EventParticipantsResponse.builder().participants(participants).build());
    }

}
