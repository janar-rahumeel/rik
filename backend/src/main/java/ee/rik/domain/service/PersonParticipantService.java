package ee.rik.domain.service;

import ee.rik.domain.PersonParticipant;

public interface PersonParticipantService {

    boolean personParticipantExists(Long eventId, String nationalIdentificationCode);

    PersonParticipant getPersonParticipant(Long id);

    PersonParticipant createPersonParticipant(Long eventId, PersonParticipant personParticipant);

    void modifyPersonParticipant(Long id, PersonParticipant personParticipant);

    void removePersonParticipant(Long id);

}
