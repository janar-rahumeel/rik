package ee.rik.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Person {

    private String firstName;

    private String lastName;

    private String nationalIdentificationCode;

}
