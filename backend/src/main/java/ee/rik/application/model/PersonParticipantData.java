package ee.rik.application.model;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
@Builder
public class PersonParticipantData {

    @Length(min = 3, message = "{domain.constraints.PersonParticipant_FirstNameLength.message}")
    @NotBlank
    private String firstName;

    @Length(min = 3, message = "{domain.constraints.PersonParticipant_LastNameLength.message}")
    @NotBlank
    private String lastName;

    @Length(min = 11, max = 11, message = "{domain.constraints.PersonParticipant_NationalIdentificationCodeLength.message}")
    @NotBlank
    private String nationalIdentificationCode;

    @NotNull
    private Integer paymentTypeId;

    @Length(max = 1500, message = "{domain.constraints.PersonParticipant_AdditionalInformationMaximumLength.message}")
    @Nullable
    private String additionalInformation;

}
