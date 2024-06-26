package ee.rik.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EventParticipant {

    private String id;

    private String name;

    private String identityCode;

}
