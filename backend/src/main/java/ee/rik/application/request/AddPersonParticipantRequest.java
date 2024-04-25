package ee.rik.application.request;

import ee.rik.domain.PersonParticipant;
import jakarta.validation.Valid;
import lombok.Getter;

@Getter
public class AddPersonParticipantRequest {

    @Valid
    private PersonParticipant personParticipant;

}
