package ee.rik.domain.service;

import ee.rik.domain.PersonParticipant;

public interface PersonParticipantService {

    void modifyPersonParticipant(Long id, PersonParticipant personParticipant);

    void deletePersonParticipant(Long id);

}
