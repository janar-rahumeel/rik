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

    private final NationalIdentificationCodeValidationService nationalIdentificationCodeValidationService;
    private final EventPersonParticipantRepository eventPersonParticipantRepository;
    private final PersonRepository personRepository;

    @Override
    public boolean personParticipantExists(Long eventId, String nationalIdentificationCode) {
        return eventPersonParticipantRepository
                .existsByEventIdAndNationalIdentificationCode(eventId, nationalIdentificationCode);
    }

    @Override
    public PersonParticipant getPersonParticipant(Long id) {
        return eventPersonParticipantRepository.get(id);
    }

    @Override
    public PersonParticipant createPersonParticipant(Long eventId, PersonParticipant personParticipant) {
        String nationalIdentificationCode = personParticipant.getNationalIdentificationCode();
        if (!nationalIdentificationCodeValidationService.isValid(nationalIdentificationCode)) {
            throw new EntityFieldNotValidException(
                    "personParticipant.nationalIdentificationCode",
                    EntityFieldErrorCodeConstant.PersonParticipant.NATIONAL_IDENTIFICATION_CODE_INVALID);
        }
        if (eventPersonParticipantRepository
                .existsByEventIdAndNationalIdentificationCode(eventId, nationalIdentificationCode)) {
            throw new EntityFieldNotValidException(
                    "personParticipant.general",
                    EntityFieldErrorCodeConstant.EventParticipant.PERSON_ALREADY_ADDED);
        }
        Pair<Long, Person> pair = personRepository.findByNationalIdentificationCode(nationalIdentificationCode)
                .orElseGet(() -> {
                    Long id = personRepository.create(
                            Person.builder()
                                    .firstName(personParticipant.getFirstName())
                                    .lastName(personParticipant.getLastName())
                                    .nationalIdentificationCode(nationalIdentificationCode)
                                    .build());
                    return Pair.of(id, personRepository.get(id));
                });
        Long id = eventPersonParticipantRepository.create(
                eventId,
                pair.getFirst(),
                personParticipant.getPaymentTypeId(),
                personParticipant.getAdditionalInformation());
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
        Person person = Person.builder()
                .firstName(personParticipant.getFirstName())
                .lastName(personParticipant.getLastName())
                .nationalIdentificationCode(personParticipant.getNationalIdentificationCode())
                .build();
        personRepository.modify(person);
        eventPersonParticipantRepository
                .modify(id, personParticipant.getPaymentTypeId(), personParticipant.getAdditionalInformation());
    }

    @Override
    public void removePersonParticipant(Long id) {
        eventPersonParticipantRepository.remove(id);
    }

}
