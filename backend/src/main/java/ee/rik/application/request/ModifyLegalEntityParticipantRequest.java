package ee.rik.application.request;

import jakarta.validation.Valid;

import ee.rik.application.GenerateTypescript;
import ee.rik.application.model.LegalEntityParticipantData;

import lombok.Getter;

@Getter
@GenerateTypescript
public class ModifyLegalEntityParticipantRequest {

    @Valid
    private LegalEntityParticipantData legalEntityParticipant;

}
