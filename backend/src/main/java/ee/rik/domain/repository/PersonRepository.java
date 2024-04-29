package ee.rik.domain.repository;

import java.util.Optional;

import ee.rik.domain.Person;

import org.springframework.data.util.Pair;

public interface PersonRepository {

    Optional<Pair<Long, Person>> findByNationalIdentificationCode(String nationalIdentificationCode);

    Person get(Long id);

    Long create(Person person);

    void modify(Long id, Person person);

}
