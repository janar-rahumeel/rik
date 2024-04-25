package ee.rik.application.response;

import ee.rik.application.GenerateTypescript;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@GenerateTypescript
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class EntityFieldValidationError {

    private String fieldName;

    private String validationErrorMessage;

}
