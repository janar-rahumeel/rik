package ee.rik.domain;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
@Builder
public class LegalEntityParticipant {

    @Length(min = 3, message = "{domain.constraints.LegalEntityParticipant_NameLength.message}")
    @NotBlank
    private String name;

    @Length(min = 11, max = 11, message = "{domain.constraints.LegalEntityParticipant_RegistrationCodeLength.message}")
    @NotBlank
    private String registrationCode;

    @NotNull
    @Positive(message = "{domain.constraints.LegalEntityParticipant_ParticipantCountMustBePositive.message}")
    private Integer participantCount;

    @NotNull
    private Integer paymentTypeId;

    @Nullable
    private String additionalInformation;

}
