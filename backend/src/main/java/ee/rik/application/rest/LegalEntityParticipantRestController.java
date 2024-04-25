package ee.rik.application.rest;

import jakarta.validation.Valid;

import ee.rik.application.request.ModifyLegalEntityParticipantRequest;
import ee.rik.domain.service.LegalEntityParticipantService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/legal-entity-participants")
@RequiredArgsConstructor
public class LegalEntityParticipantRestController {

    private final LegalEntityParticipantService legalEntityParticipantService;

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
