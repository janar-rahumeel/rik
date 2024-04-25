package ee.rik.application.request;

import jakarta.validation.Valid;

import ee.rik.domain.PersonParticipant;

import lombok.Getter;

@Getter
public class CreatePersonParticipantRequest {

    @Valid
    private PersonParticipant personParticipant;

}
