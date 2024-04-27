package ee.rik.domain.repository;

import java.util.Optional;

import ee.rik.domain.PersonParticipant;

import org.springframework.data.util.Pair;

public interface PersonParticipantRepository {

    Optional<Pair<Long, PersonParticipant>> findByNationalIdentificationCode(String nationalIdentificationCode);

    PersonParticipant get(Long id);

    Long create(PersonParticipant personParticipant);

    void modify(Long id, PersonParticipant personParticipant);

}
