package ee.rik.application.request;

import ee.rik.application.GenerateTypescript;
import ee.rik.domain.LegalEntityParticipant;
import jakarta.validation.Valid;

import lombok.Getter;

@Getter
@GenerateTypescript
public class AddLegalEntityParticipantRequest {

    @Valid
    private LegalEntityParticipant legalEntityParticipant;

}
