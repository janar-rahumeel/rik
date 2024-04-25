package ee.rik.application.request;

import ee.rik.domain.LegalEntityParticipant;
import jakarta.validation.Valid;

import lombok.Getter;

@Getter
public class AddLegalEntityParticipantRequest {

    @Valid
    private LegalEntityParticipant legalEntityParticipant;

}
