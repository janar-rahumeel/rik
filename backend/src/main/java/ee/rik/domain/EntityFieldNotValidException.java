package ee.rik.domain;

import lombok.Getter;

@Getter
public class EntityFieldNotValidException extends RuntimeException {

    private final String fieldName;
    private final String errorCode;

    public EntityFieldNotValidException(String fieldName, String errorCode) {
        super();
        this.fieldName = fieldName;
        this.errorCode = errorCode;
    }

}
