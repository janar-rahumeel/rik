package ee.rik.application.request;

import jakarta.validation.Valid;

import ee.rik.application.GenerateTypescript;

import lombok.Getter;

@Getter
@GenerateTypescript
public class CreateEventRequest {

    @Valid
    private EventPayload event;

}
