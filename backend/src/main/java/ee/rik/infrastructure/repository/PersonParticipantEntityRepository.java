package ee.rik.infrastructure.repository;

import java.util.Optional;

import ee.rik.infrastructure.entity.PersonParticipantEntity;

import org.springframework.data.repository.CrudRepository;

public interface PersonParticipantEntityRepository extends CrudRepository<PersonParticipantEntity, Long> {

    Optional<PersonParticipantEntity> findByNationalIdentificationCode(String nationalIdentificationCode);

    void flush();

}
