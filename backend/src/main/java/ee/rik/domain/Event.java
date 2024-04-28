package ee.rik.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class Event {

    private String name;

    private LocalDateTime startDateTime;

    private String location;

    private String description;

}
