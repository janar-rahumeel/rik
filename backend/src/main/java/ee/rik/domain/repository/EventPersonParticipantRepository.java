package ee.rik.domain.repository;

import ee.rik.domain.PersonParticipant;

public interface EventPersonParticipantRepository {

    boolean existsByEventIdAndNationalIdentificationCode(Long eventId, String nationalIdentificationCode);

    PersonParticipant get(Long eventPersonParticipantId);

    Long create(Long eventId, Long personId, PersonParticipant personParticipant);

    void modify(Long eventPersonParticipantId, PersonParticipant personParticipant);

    void remove(Long eventPersonParticipantId);

}
