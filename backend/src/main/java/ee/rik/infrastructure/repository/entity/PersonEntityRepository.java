package ee.rik.infrastructure.repository.entity;

import java.util.Optional;

import ee.rik.infrastructure.entity.PersonEntity;

import org.springframework.data.repository.CrudRepository;

public interface PersonEntityRepository extends CrudRepository<PersonEntity, Long> {

    Optional<PersonEntity> findByNationalIdentificationCode(String nationalIdentificationCode);

}
