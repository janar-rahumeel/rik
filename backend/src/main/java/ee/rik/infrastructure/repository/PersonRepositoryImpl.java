package ee.rik.infrastructure.repository;

import java.util.Optional;

import jakarta.persistence.EntityNotFoundException;

import ee.rik.domain.Person;
import ee.rik.domain.repository.PersonRepository;
import ee.rik.infrastructure.entity.PersonEntity;
import ee.rik.infrastructure.repository.entity.PersonEntityRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PersonRepositoryImpl implements PersonRepository {

    private final PersonEntityRepository personEntityRepository;

    @Override
    public Optional<Pair<Long, Person>> findByNationalIdentificationCode(String nationalIdentificationCode) {
        return personEntityRepository.findByNationalIdentificationCode(nationalIdentificationCode)
                .map(personEntity -> Pair.of(personEntity.getId(), toDomain(personEntity)));
    }

    @Override
    public Person get(Long id) {
        PersonEntity personEntity = personEntityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No PersonEntity found: " + id));
        return toDomain(personEntity);
    }

    private static Person toDomain(PersonEntity personEntity) {
        return Person.builder()
                .firstName(personEntity.getFirstName())
                .lastName(personEntity.getLastName())
                .nationalIdentificationCode(personEntity.getNationalIdentificationCode())
                .build();
    }

    @Override
    @Transactional
    public Long create(Person person) {
        PersonEntity personEntity = toEntity(person);
        return personEntityRepository.save(personEntity).getId();
    }

    private static PersonEntity toEntity(Person person) {
        return PersonEntity.builder()
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .nationalIdentificationCode(person.getNationalIdentificationCode())
                .build();
    }

    @Override
    @Transactional
    public void modify(Person person) {
        String nationalIdentificationCode = person.getNationalIdentificationCode();
        PersonEntity personEntity = personEntityRepository.findByNationalIdentificationCode(nationalIdentificationCode)
                .orElseThrow(() -> new EntityNotFoundException("No PersonEntity found: " + nationalIdentificationCode));
        if (!personEntity.getFirstName().equals(person.getFirstName())) {
            personEntity.setFirstName(person.getFirstName());
        }
        if (!personEntity.getLastName().equals(person.getLastName())) {
            personEntity.setLastName(person.getLastName());
        }
    }

}
