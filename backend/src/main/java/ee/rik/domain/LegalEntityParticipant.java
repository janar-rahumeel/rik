package ee.rik.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LegalEntityParticipant {

    private String name;

    private String registrationCode;

    private Integer participantCount;

    private Integer paymentTypeId;

    private String additionalInformation;

}
