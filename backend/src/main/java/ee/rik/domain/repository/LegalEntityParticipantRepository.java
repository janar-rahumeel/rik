package ee.rik.domain.repository;

import ee.rik.domain.LegalEntityParticipant;

public interface LegalEntityParticipantRepository {

    LegalEntityParticipant get(Long id);

    Long create(LegalEntityParticipant legalEntityParticipant);

    void modify(Long id, LegalEntityParticipant legalEntityParticipant);

    void delete(Long id);

}
