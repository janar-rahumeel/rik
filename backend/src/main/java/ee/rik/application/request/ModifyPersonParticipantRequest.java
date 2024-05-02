package ee.rik.application.request;

import jakarta.validation.Valid;

import ee.rik.application.GenerateTypescript;
import ee.rik.application.model.PersonParticipantData;

import lombok.Getter;

@Getter
@GenerateTypescript
public class ModifyPersonParticipantRequest {

    @Valid
    private PersonParticipantData personParticipant;

}
