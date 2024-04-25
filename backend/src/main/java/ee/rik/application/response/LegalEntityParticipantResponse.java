package ee.rik.application.response;

import ee.rik.domain.LegalEntityParticipant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LegalEntityParticipantResponse {

    private LegalEntityParticipant legalEntityParticipant;

}
