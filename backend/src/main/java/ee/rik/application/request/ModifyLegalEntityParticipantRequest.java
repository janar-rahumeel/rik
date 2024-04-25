package ee.rik.application.request;

import ee.rik.application.GenerateTypescript;
import jakarta.validation.Valid;

import ee.rik.domain.LegalEntityParticipant;
import lombok.Getter;

@Getter
@GenerateTypescript
public class ModifyLegalEntityParticipantRequest {

    @Valid
    private LegalEntityParticipant legalEntityParticipant;

}
