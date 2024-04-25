package ee.rik.domain.service;

import ee.rik.domain.PersonParticipant;

public interface PersonParticipantService {

    Long createPersonParticipant(PersonParticipant personParticipant);

    void modifyPersonParticipant(Long id, PersonParticipant personParticipant);

    void deletePersonParticipant(Long id);

}
