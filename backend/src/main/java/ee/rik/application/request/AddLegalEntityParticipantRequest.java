package ee.rik.application.request;

import jakarta.validation.Valid;

import ee.rik.application.GenerateTypescript;
import ee.rik.application.model.LegalEntityParticipantData;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
@GenerateTypescript
public class AddLegalEntityParticipantRequest {

    @NotNull
    private Long eventId;

    @Valid
    private LegalEntityParticipantData legalEntityParticipant;

}
