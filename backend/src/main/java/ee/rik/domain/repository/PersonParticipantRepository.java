package ee.rik.domain.repository;

import ee.rik.domain.PersonParticipant;

public interface PersonParticipantRepository {

    PersonParticipant get(Long id);

    Long create(PersonParticipant personParticipant);

    void modify(Long id, PersonParticipant personParticipant);

    void delete(Long id);

}
