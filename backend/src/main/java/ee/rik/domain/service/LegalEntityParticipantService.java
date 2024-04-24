package ee.rik.domain.service;

import ee.rik.domain.LegalEntityParticipant;

public interface LegalEntityParticipantService {

    Long createLegalEntityParticipant(LegalEntityParticipant legalEntityParticipant);

    void modifyLegalEntityParticipant(Long id, LegalEntityParticipant legalEntityParticipant);

    void deleteLegalEntityParticipant(Long id);

}
