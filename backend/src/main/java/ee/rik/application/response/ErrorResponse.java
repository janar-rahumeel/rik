package ee.rik.application.response;

import ee.rik.application.GenerateTypescript;
import jakarta.annotation.Nullable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Builder
@NoArgsConstructor
@GenerateTypescript
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorResponse {

    @Nullable
    private UUID uuid;

    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();

    @Nullable
    private String message;

    @Builder.Default
    private List<EntityFieldValidationError> entityFieldValidationErrors = new ArrayList<>();

}
