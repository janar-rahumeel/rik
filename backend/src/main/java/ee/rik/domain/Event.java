package ee.rik.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class Event {

    @NotBlank
    private String name;

    @NotNull
    private LocalDateTime startDateTime;

    @NotBlank
    private String location;

    @NotBlank
    private String description;

}
