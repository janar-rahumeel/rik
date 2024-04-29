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

@Table(name = "PERSON")
@Setter
@Getter
@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PersonEntity extends AbstractEntity<Long> {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PERSON")
    @SequenceGenerator(name = "SEQ_PERSON", initialValue = 1000, allocationSize = 1)
    private Long id;

    @Column(name = "FIRST_NAME")
    @NotBlank
    private String firstName;

    @Column(name = "LAST_NAME")
    @NotBlank
    private String lastName;

    @Column(name = "NATIONAL_IDENTIFICATION_CODE")
    @NotBlank
    private String nationalIdentificationCode;

}
