package ee.rik.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode(of = "id")
public class ListEvent {

    private Long id;

    private String name;

    private String startDate;

}
