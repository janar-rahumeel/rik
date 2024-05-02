package ee.rik.application.mapper;

import ee.rik.application.model.PersonParticipantData;
import ee.rik.domain.PersonParticipant;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class PersonParticipantMapper {

    public abstract PersonParticipantData map(PersonParticipant personParticipant);

    public abstract PersonParticipant map(PersonParticipantData personParticipantData);

}
