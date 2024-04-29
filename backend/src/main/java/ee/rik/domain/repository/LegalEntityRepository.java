package ee.rik.domain.repository;

import java.util.Optional;

import ee.rik.domain.LegalEntity;

import org.springframework.data.util.Pair;

public interface LegalEntityRepository {

    Optional<Pair<Long, LegalEntity>> findByRegistrationCode(String registrationCode);

    LegalEntity get(Long id);

    Long create(LegalEntity person);

    void modify(LegalEntity legalEntity);

}
