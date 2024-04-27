package ee.rik.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PersonParticipant {

    private String firstName;

    private String lastName;

    private String nationalIdentificationCode;

    private Integer paymentTypeId;

    private String additionalInformation;

}
