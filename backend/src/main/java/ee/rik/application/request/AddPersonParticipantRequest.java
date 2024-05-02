package ee.rik.application.request;

import jakarta.validation.Valid;

import ee.rik.application.GenerateTypescript;
import ee.rik.application.model.PersonParticipantData;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
@GenerateTypescript
public class AddPersonParticipantRequest {

    @NotNull
    private Long eventId;

    @Valid
    private PersonParticipantData personParticipant;

}
