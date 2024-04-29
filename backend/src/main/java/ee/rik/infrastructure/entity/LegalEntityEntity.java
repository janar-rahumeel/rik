package ee.rik.infrastructure.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table(name = "LEGAL_ENTITY")
@Getter
@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LegalEntityEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_LEGAL_ENTITY")
    @SequenceGenerator(name = "SEQ_LEGAL_ENTITY", initialValue = 1000, allocationSize = 1)
    private Long id;

    @Setter
    @Column(name = "NAME")
    @NotBlank
    private String name;

    @Column(name = "REGISTRATION_CODE")
    @NotBlank
    private String registrationCode;

}
