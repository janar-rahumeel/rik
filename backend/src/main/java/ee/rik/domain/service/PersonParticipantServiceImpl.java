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
    public PersonParticipant getPersonParticipant(Long id) {
        return personParticipantRepository.get(id);
    }

    @Override
    public void modifyPersonParticipant(Long id, PersonParticipant personParticipant) {
        personParticipantRepository.modify(id, personParticipant);
    }

}
