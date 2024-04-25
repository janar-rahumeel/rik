package ee.rik.application.response;

import ee.rik.domain.PersonParticipant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PersonParticipantResponse {

    private PersonParticipant personParticipant;

}
