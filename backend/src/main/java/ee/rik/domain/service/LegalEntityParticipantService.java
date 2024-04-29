package ee.rik.domain.service;

import ee.rik.domain.LegalEntityParticipant;

public interface LegalEntityParticipantService {

    boolean legalEntityParticipantExists(Long eventId, String registrationCode);

    LegalEntityParticipant getLegalEntityParticipant(Long id);

    LegalEntityParticipant createLegalEntityParticipant(Long eventId, LegalEntityParticipant legalEntityParticipant);

    void modifyLegalEntityParticipant(Long id, LegalEntityParticipant legalEntityParticipant);

    void removeLegalEntityParticipant(Long id);

}
