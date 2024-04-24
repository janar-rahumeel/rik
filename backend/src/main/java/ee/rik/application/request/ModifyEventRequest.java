package ee.rik.application.request;

import ee.rik.domain.Event;
import jakarta.validation.Valid;
import lombok.Getter;

@Getter
public class ModifyEventRequest {

    @Valid
    private Event event;

}
