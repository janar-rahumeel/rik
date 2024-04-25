package ee.rik.application.rest;

import jakarta.validation.Valid;

import ee.rik.application.request.ModifyPersonParticipantRequest;
import ee.rik.domain.service.PersonParticipantService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
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

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void modify(
            @PathVariable Long id,
            @Valid @RequestBody ModifyPersonParticipantRequest modifyPersonParticipantRequest) {
        personParticipantService.modifyPersonParticipant(id, modifyPersonParticipantRequest.getPersonParticipant());
    }

    @DeleteMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void delete(@PathVariable Long id) {
        personParticipantService.deletePersonParticipant(id);
    }

}
