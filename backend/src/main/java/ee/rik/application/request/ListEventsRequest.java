package ee.rik.application.request;

import ee.rik.application.GenerateTypescript;
import jakarta.validation.constraints.NotNull;

import lombok.Getter;

@Getter
@GenerateTypescript
public class ListEventsRequest {

    @NotNull
    private Boolean newEvents;

}
