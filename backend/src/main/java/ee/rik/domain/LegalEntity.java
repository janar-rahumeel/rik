package ee.rik.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LegalEntity {

    private String name;

    private String registrationCode;

}
