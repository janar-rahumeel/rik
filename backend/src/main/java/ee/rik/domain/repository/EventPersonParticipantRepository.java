package ee.rik.domain.repository;

import ee.rik.domain.PersonParticipant;

public interface EventPersonParticipantRepository {

    boolean existsByEventIdAndNationalIdentificationCode(Long eventId, String nationalIdentificationCode);

    PersonParticipant get(Long eventPersonParticipantId);

    Long create(Long eventId, Long personId, Integer paymentTypeId, String additionalInformation);

    void modify(Long eventPersonParticipantId, Integer paymentTypeId, String additionalInformation);

    void remove(Long eventPersonParticipantId);

}
