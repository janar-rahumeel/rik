package ee.rik.infrastructure.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Immutable;

@Table(name = "EVENT_PARTICIPANT_VIEW")
@Setter
@Getter
@Entity
@Builder
@ToString
@Immutable
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class EventParticipantEntity extends AbstractEntity<String> {

    @Id
    @Column(name = "ID")
    private String id;

    @ManyToOne
    @JoinColumn(name = "EVENT_ID")
    private EventEntity event;

    @Column(name = "NAME")
    private String name;

    @Column(name = "IDENTITY_CODE")
    private String identityCode;

    @JoinTable(
            name = "EVENT_PERSON_PARTICIPANT_X",
            joinColumns = { @JoinColumn(name = "EVENT_ID") },
            inverseJoinColumns = { @JoinColumn(name = "PERSON_PARTICIPANT_ID") })
    @ManyToMany(fetch = FetchType.LAZY)
    private Set<PersonParticipantEntity> personParticipants = new HashSet<>();

}
