package ee.rik.application.rest;

import java.net.URI;

import jakarta.validation.Valid;

import ee.rik.application.request.CreateLegalEntityParticipantRequest;
import ee.rik.application.request.ModifyLegalEntityParticipantRequest;
import ee.rik.domain.service.LegalEntityParticipantService;
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

@RestController
@RequestMapping("/legal-entity-participants")
@RequiredArgsConstructor
public class LegalEntityParticipantRestController {

    private final LegalEntityParticipantService legalEntityParticipantService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create(
            @Valid @RequestBody CreateLegalEntityParticipantRequest createLegalEntityParticipantRequest) {
        Long id = legalEntityParticipantService
                .createLegalEntityParticipant(createLegalEntityParticipantRequest.getLegalEntityParticipant());
        return ResponseEntity.created(URI.create("/events/" + id)).build();
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void modify(
            @PathVariable Long id,
            @Valid @RequestBody ModifyLegalEntityParticipantRequest modifyLegalEntityParticipantRequest) {
        legalEntityParticipantService
                .modifyLegalEntityParticipant(id, modifyLegalEntityParticipantRequest.getLegalEntityParticipant());
    }

    @DeleteMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void delete(@PathVariable Long id) {
        legalEntityParticipantService.deleteLegalEntityParticipant(id);
    }

}
