package ee.rik.infrastructure.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table(name = "LEGAL_ENTITY_PARTICIPANT")
@Setter
@Getter
@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LegalEntityParticipantEntity extends AbstractEntity<Long> {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_LEGAL_ENTITY_PARTICIPANT")
    @SequenceGenerator(name = "SEQ_LEGAL_ENTITY_PARTICIPANT", initialValue = 1000, allocationSize = 1)
    private Long id;

    @Column(name = "NAME")
    @NotBlank
    private String name;

    @Column(name = "REGISTRATION_CODE")
    @NotBlank
    private String registrationCode;

    @Column(name = "PARTICIPANT_COUNT")
    @NotNull
    private Integer participantCount;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PAYMENT_TYPE_ID")
    private PaymentTypeEntity paymentType;

    @Column(name = "ADDITIONAL_INFORMATION")
    @NotBlank
    private String additionalInformation;

}
