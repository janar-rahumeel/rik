package ee.rik.application.rest;

import jakarta.validation.Valid;

import ee.rik.application.mapper.PersonParticipantMapper;
import ee.rik.application.model.PersonParticipantData;
import ee.rik.application.request.AddPersonParticipantRequest;
import ee.rik.application.request.ModifyPersonParticipantRequest;
import ee.rik.application.response.PersonParticipantResponse;
import ee.rik.domain.PersonParticipant;
import ee.rik.domain.service.PersonParticipantService;

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
@RequestMapping("/person-participants")
@RequiredArgsConstructor
public class PersonParticipantRestController {

    private final PersonParticipantMapper personParticipantMapper;
    private final PersonParticipantService personParticipantService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PersonParticipant> getPersonParticipant(@PathVariable Long id) {
        return ResponseEntity.ok(personParticipantService.getPersonParticipant(id));
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PersonParticipantResponse> addPersonParticipant(
            @Valid @RequestBody AddPersonParticipantRequest addPersonParticipantRequest) {
        PersonParticipant newPersonParticipant = personParticipantMapper
                .map(addPersonParticipantRequest.getPersonParticipant());
        PersonParticipant personParticipant = personParticipantService
                .createPersonParticipant(addPersonParticipantRequest.getEventId(), newPersonParticipant);
        PersonParticipantData personParticipantData = personParticipantMapper.map(personParticipant);
        return ResponseEntity.ok(PersonParticipantResponse.builder().personParticipant(personParticipantData).build());
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void modifyPersonParticipant(
            @PathVariable Long id,
            @Valid @RequestBody ModifyPersonParticipantRequest modifyPersonParticipantRequest) {
        PersonParticipant personParticipant = personParticipantMapper
                .map(modifyPersonParticipantRequest.getPersonParticipant());
        personParticipantService.modifyPersonParticipant(id, personParticipant);
    }

    @DeleteMapping(value = "/{id}")
    public void removePersonParticipant(@PathVariable Long id) {
        personParticipantService.removePersonParticipant(id);
    }

}
