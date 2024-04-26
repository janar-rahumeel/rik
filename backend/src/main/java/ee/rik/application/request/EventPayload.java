package ee.rik.application.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EventPayload {

    @NotBlank
    private String name;

    @NotNull
    private String startDateTime;

    @NotBlank
    private String location;

    @NotBlank
    private String description;

}
