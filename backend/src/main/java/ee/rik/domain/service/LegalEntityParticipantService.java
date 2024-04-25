package ee.rik.domain.service;

import ee.rik.domain.LegalEntityParticipant;

public interface LegalEntityParticipantService {

    void modifyLegalEntityParticipant(Long id, LegalEntityParticipant legalEntityParticipant);

    void deleteLegalEntityParticipant(Long id);

}
