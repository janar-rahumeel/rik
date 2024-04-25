package ee.rik.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import ee.rik.domain.validator.NationalIdentificationCodeConstraint;

import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
@Builder
public class PersonParticipant {

    @Length(min = 3, message = "{domain.constraints.PersonParticipant_FirstNameLength.message}")
    @NotBlank
    private String firstName;

    @Length(min = 3, message = "{domain.constraints.PersonParticipant_LastNameLength.message}")
    @NotBlank
    private String lastName;

    @NotBlank
    @NationalIdentificationCodeConstraint
    private String nationalIdentificationCode;

    @NotNull
    private Integer paymentTypeId;

    @Length(max = 1500, message = "{domain.constraints.PersonParticipant_AdditionalInformationMaximumLength.message}")
    @NotBlank
    private String additionalInformation;

}
