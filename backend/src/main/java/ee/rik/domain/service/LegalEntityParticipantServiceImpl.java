package ee.rik.domain.service;

import ee.rik.domain.EntityFieldErrorCodeConstant;
import ee.rik.domain.EntityFieldNotValidException;
import ee.rik.domain.LegalEntity;
import ee.rik.domain.LegalEntityParticipant;
import ee.rik.domain.repository.EventLegalEntityParticipantRepository;
import ee.rik.domain.repository.LegalEntityRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LegalEntityParticipantServiceImpl implements LegalEntityParticipantService {

    private final EventLegalEntityParticipantRepository eventLegalEntityParticipantRepository;
    private final LegalEntityRepository legalEntityRepository;

    @Override
    public LegalEntityParticipant getLegalEntityParticipant(Long id) {
        return eventLegalEntityParticipantRepository.get(id);
    }

    @Override
    public LegalEntityParticipant createLegalEntityParticipant(Long eventId, LegalEntityParticipant legalEntityParticipant) {
        String registrationCode = legalEntityParticipant.getRegistrationCode();
        if (eventLegalEntityParticipantRepository.existsByEventIdAndRegistrationCode(eventId, registrationCode)) {
            throw new EntityFieldNotValidException(
                    "legalEntityParticipant.general",
                    EntityFieldErrorCodeConstant.EventParticipant.LEGAL_ENTITY_ALREADY_ADDED);
        }
        Pair<Long, LegalEntity> pair = legalEntityRepository.findByRegistrationCode(registrationCode).orElseGet(() -> {
            Long id = legalEntityRepository.create(
                    LegalEntity.builder().name(legalEntityParticipant.getName()).registrationCode(registrationCode).build());
            return Pair.of(id, legalEntityRepository.get(id));
        });
        Long id = eventLegalEntityParticipantRepository.create(
                eventId,
                pair.getFirst(),
                legalEntityParticipant.getPaymentTypeId(),
                legalEntityParticipant.getParticipantCount(),
                legalEntityParticipant.getAdditionalInformation());
        return eventLegalEntityParticipantRepository.get(id);
    }

    @Override
    public void modifyLegalEntityParticipant(Long id, LegalEntityParticipant legalEntityParticipant) {
        LegalEntityParticipant existingLegalEntityParticipant = eventLegalEntityParticipantRepository.get(id);
        if (!existingLegalEntityParticipant.getRegistrationCode().equals(legalEntityParticipant.getRegistrationCode())) {
            throw new EntityFieldNotValidException(
                    "legalEntityParticipant.registrationCode",
                    EntityFieldErrorCodeConstant.LegalEntityParticipant.REGISTRATION_CODE_MISMATCH);
        }
        LegalEntity legalEntity = toDomain(legalEntityParticipant);
        legalEntityRepository.modify(legalEntity);
        eventLegalEntityParticipantRepository.modify(
                id,
                legalEntityParticipant.getPaymentTypeId(),
                legalEntityParticipant.getParticipantCount(),
                legalEntityParticipant.getAdditionalInformation());
    }

    private static LegalEntity toDomain(LegalEntityParticipant legalEntityParticipant) {
        return LegalEntity.builder()
                .name(legalEntityParticipant.getName())
                .registrationCode(legalEntityParticipant.getRegistrationCode())
                .build();
    }

    @Override
    public void removeLegalEntityParticipant(Long id) {
        eventLegalEntityParticipantRepository.remove(id);
    }

}
