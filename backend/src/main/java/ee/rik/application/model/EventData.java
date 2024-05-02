package ee.rik.application.model;

import jakarta.validation.constraints.NotBlank;

import ee.rik.application.validator.EventStartDateTimeConstraint;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EventData {

    @NotBlank
    private String name;

    @EventStartDateTimeConstraint
    private String startDateTime;

    @NotBlank
    private String location;

    @NotBlank
    private String description;

}
