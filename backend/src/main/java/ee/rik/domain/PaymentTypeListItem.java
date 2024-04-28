package ee.rik.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode(of = "id")
public class PaymentTypeListItem {

    private Integer id;

    private String name;

}
