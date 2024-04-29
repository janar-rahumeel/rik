package ee.rik.infrastructure.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
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

@Table(name = "EVENT")
@Setter
@Getter
@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class EventEntity extends AbstractEntity<Long> {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_EVENT")
    @SequenceGenerator(name = "SEQ_EVENT", initialValue = 1000, allocationSize = 1)
    private Long id;

    @Column(name = "NAME")
    @NotBlank
    private String name;

    @Column(name = "START_DATETIME")
    @NotNull
    private LocalDateTime startDateTime;

    @Column(name = "LOCATION")
    @NotBlank
    private String location;

    @Column(name = "DESCRIPTION")
    @NotBlank
    private String description;

    @OneToMany(mappedBy = "event", cascade = CascadeType.REMOVE)
    @Builder.Default
    private Set<EventPersonParticipantEntity> eventPersonParticipantEntities = new HashSet<>();

    @JoinTable(
            name = "EVENT_LEGAL_ENTITY_PARTICIPANT_X",
            joinColumns = { @JoinColumn(name = "EVENT_ID") },
            inverseJoinColumns = { @JoinColumn(name = "LEGAL_ENTITY_PARTICIPANT_ID") })
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @Builder.Default
    private Set<LegalEntityParticipantEntity> legalEntityParticipantEntities = new HashSet<>();

}
