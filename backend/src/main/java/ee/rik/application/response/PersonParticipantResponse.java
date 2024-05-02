package ee.rik.application.response;

import ee.rik.application.GenerateTypescript;
import ee.rik.application.model.PersonParticipantData;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@GenerateTypescript
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PersonParticipantResponse {

    private PersonParticipantData personParticipant;

}
