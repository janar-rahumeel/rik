package ee.rik.domain.service;

import ee.rik.domain.PersonParticipant;

public interface PersonParticipantService {

    PersonParticipant getPersonParticipant(Long id);

    void modifyPersonParticipant(Long id, PersonParticipant personParticipant);

}
