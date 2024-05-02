package ee.rik.application.mapper;

import ee.rik.application.model.LegalEntityParticipantData;
import ee.rik.domain.LegalEntityParticipant;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class LegalEntityParticipantMapper {

    public abstract LegalEntityParticipantData map(LegalEntityParticipant legalEntityParticipant);

    public abstract LegalEntityParticipant map(LegalEntityParticipantData legalEntityParticipantData);

}
