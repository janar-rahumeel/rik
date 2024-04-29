package ee.rik.domain.repository;

import ee.rik.domain.LegalEntityParticipant;

public interface EventLegalEntityParticipantRepository {

    boolean existsByEventIdAndRegistrationCode(Long eventId, String registrationCode);

    LegalEntityParticipant get(Long eventLegalEntityParticipantId);

    Long create(
            Long eventId,
            Long legalEntityId,
            Integer paymentTypeId,
            Integer participantCount,
            String additionalInformation);

    void modify(
            Long eventLegalEntityParticipantId,
            Integer paymentTypeId,
            Integer participantCount,
            String additionalInformation);

    void remove(Long eventLegalEntityParticipantId);

}
