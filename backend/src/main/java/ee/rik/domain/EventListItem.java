package ee.rik.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode(of = "id")
public class EventListItem {

    private Long id;

    private String name;

    private String startDate;

    private String location;

    private Integer totalParticipantCount;

}
