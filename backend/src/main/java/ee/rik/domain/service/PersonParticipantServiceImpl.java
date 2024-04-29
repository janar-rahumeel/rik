package ee.rik.domain.service;

import ee.rik.domain.EntityFieldErrorCodeConstant;
import ee.rik.domain.EntityFieldNotValidException;
import ee.rik.domain.Person;
import ee.rik.domain.PersonParticipant;
import ee.rik.domain.repository.EventPersonParticipantRepository;
import ee.rik.domain.repository.PersonRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonParticipantServiceImpl implements PersonParticipantService {

    private final EventPersonParticipantRepository eventPersonParticipantRepository;
    private final PersonRepository personRepository;

    @Override
    public PersonParticipant getPersonParticipant(Long id) {
        return eventPersonParticipantRepository.get(id);
    }

    @Override
    public PersonParticipant createPersonParticipant(Long eventId, PersonParticipant personParticipant) {
        Pair<Long, Person> pair = personRepository
                .findByNationalIdentificationCode(personParticipant.getNationalIdentificationCode())
                .orElseGet(() -> {
                    Long id = personRepository.create(
                            Person.builder()
                                    .firstName(personParticipant.getFirstName())
                                    .lastName(personParticipant.getLastName())
                                    .nationalIdentificationCode(personParticipant.getNationalIdentificationCode())
                                    .build());
                    return Pair.of(id, personRepository.get(id));
                });
        Long id = eventPersonParticipantRepository.create(eventId, pair.getFirst(), personParticipant);
        return eventPersonParticipantRepository.get(id);
    }

    @Override
    public void modifyPersonParticipant(Long id, PersonParticipant personParticipant) {
        PersonParticipant existingPersonParticipant = eventPersonParticipantRepository.get(id);
        if (!existingPersonParticipant.getNationalIdentificationCode()
                .equals(personParticipant.getNationalIdentificationCode())) {
            throw new EntityFieldNotValidException(
                    "personParticipant.nationalIdentificationCode",
                    EntityFieldErrorCodeConstant.PersonParticipant.NATIONAL_IDENTIFICATION_CODE_MISMATCH);
        }
        Pair<Long, Person> pair = personRepository
                .findByNationalIdentificationCode(personParticipant.getNationalIdentificationCode())
                .get(); // TODO
        personRepository.modify(
                pair.getFirst(),
                Person.builder().firstName(personParticipant.getFirstName()).lastName(personParticipant.getLastName()).build()); // TODO
                                                                                                                                 // use
                                                                                                                                 // ID
                                                                                                                                 // for
                                                                                                                                 // lookup?!?!?
        eventPersonParticipantRepository.modify(id, personParticipant);
    }

    @Override
    public void removePersonParticipant(Long id) {
        eventPersonParticipantRepository.remove(id);
    }

}
