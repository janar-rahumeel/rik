package ee.rik.application.request;

import jakarta.validation.Valid;

import ee.rik.domain.LegalEntityParticipant;
import lombok.Getter;

@Getter
public class ModifyLegalEntityParticipantRequest {

    @Valid
    private LegalEntityParticipant legalEntityParticipant;

}
