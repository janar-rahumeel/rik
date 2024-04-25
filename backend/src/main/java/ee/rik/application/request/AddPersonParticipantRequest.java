package ee.rik.application.request;

import ee.rik.application.GenerateTypescript;
import ee.rik.domain.PersonParticipant;
import jakarta.validation.Valid;
import lombok.Getter;

@Getter
@GenerateTypescript
public class AddPersonParticipantRequest {

    @Valid
    private PersonParticipant personParticipant;

}
