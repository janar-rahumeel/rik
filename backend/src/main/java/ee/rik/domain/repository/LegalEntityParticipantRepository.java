package ee.rik.domain.repository;

import java.util.Optional;

import ee.rik.domain.LegalEntityParticipant;

import org.springframework.data.util.Pair;

public interface LegalEntityParticipantRepository {

    Optional<Pair<Long, LegalEntityParticipant>> findByRegistrationCode(String registrationCode);

    LegalEntityParticipant get(Long id);

    Long create(LegalEntityParticipant legalEntityParticipant);

    void modify(Long id, LegalEntityParticipant legalEntityParticipant);

    void delete(Long id);


}
