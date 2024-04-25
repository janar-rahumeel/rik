package ee.rik.domain.service;

import ee.rik.domain.PersonParticipant;
import ee.rik.domain.repository.PersonParticipantRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonParticipantServiceImpl implements PersonParticipantService {

    private final PersonParticipantRepository personParticipantRepository;

    @Override
    public Long createPersonParticipant(PersonParticipant personParticipant) {
        return personParticipantRepository.create(personParticipant);
    }

    @Override
    public void modifyPersonParticipant(Long id, PersonParticipant personParticipant) {
        personParticipantRepository.modify(id, personParticipant);
    }

    @Override
    public void deletePersonParticipant(Long id) {
        personParticipantRepository.delete(id);
    }

}
