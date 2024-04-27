package ee.rik.domain.service;

import ee.rik.domain.LegalEntityParticipant;

public interface LegalEntityParticipantService {

    LegalEntityParticipant getLegalEntityParticipant(Long id);

    void modifyLegalEntityParticipant(Long id, LegalEntityParticipant legalEntityParticipant);

}
