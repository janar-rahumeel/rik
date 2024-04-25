package ee.rik.application.request;

import ee.rik.application.GenerateTypescript;
import ee.rik.domain.Event;
import jakarta.validation.Valid;
import lombok.Getter;

@Getter
@GenerateTypescript
public class ModifyEventRequest {

    @Valid
    private Event event;

}
