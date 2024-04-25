package ee.rik.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode(of = "id")
public class EventParticipant {

    private String id;

    private String name;

    private String identityCode;

}
