package ee.rik.application.rest;

import jakarta.validation.Valid;

import ee.rik.application.request.ModifyPersonParticipantRequest;
import ee.rik.domain.PersonParticipant;
import ee.rik.domain.service.PersonParticipantService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/person-participants")
@RequiredArgsConstructor
public class PersonParticipantRestController {

    private final PersonParticipantService personParticipantService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PersonParticipant> getPersonParticipant(@PathVariable Long id) {
        return ResponseEntity.ok(personParticipantService.get(id));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void modifyPersonParticipant(
            @PathVariable Long id,
            @Valid @RequestBody ModifyPersonParticipantRequest modifyPersonParticipantRequest) {
        personParticipantService.modifyPersonParticipant(id, modifyPersonParticipantRequest.getPersonParticipant());
    }

    @DeleteMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deletePersonParticipant(@PathVariable Long id) {
        personParticipantService.deletePersonParticipant(id);
    }

}
