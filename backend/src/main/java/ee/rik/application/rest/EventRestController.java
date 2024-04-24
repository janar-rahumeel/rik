package ee.rik.application.rest;

import ee.rik.application.request.CreateEventRequest;
import ee.rik.application.request.ModifyEventRequest;
import ee.rik.domain.service.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventRestController {

    private final EventService eventService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create(@Valid @RequestBody CreateEventRequest createEventRequest) {
        Long id = eventService.createEvent(createEventRequest.getEvent());
        return ResponseEntity.created(URI.create("/events/" + id)).build();
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void modify(@PathVariable Long id, @Valid @RequestBody ModifyEventRequest modifyEventRequest) {
        eventService.modifyEvent(id, modifyEventRequest.getEvent());
    }

    @DeleteMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void delete(@PathVariable Long id) {
        eventService.deleteEvent(id);
    }

}
