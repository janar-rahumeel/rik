package ee.rik.infrastructure.repository;

import java.util.Optional;

import jakarta.persistence.EntityNotFoundException;

import ee.rik.domain.LegalEntityParticipant;
import ee.rik.domain.repository.LegalEntityParticipantRepository;
import ee.rik.infrastructure.entity.LegalEntityParticipantEntity;
import ee.rik.infrastructure.entity.PaymentTypeEntity;

import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LegalEntityParticipantRepositoryImpl implements LegalEntityParticipantRepository {

    private final LegalEntityParticipantEntityRepository legalEntityParticipantEntityRepository;
    private final PaymentTypeEntityRepository paymentTypeEntityRepository;

    @Override
    public Optional<Pair<Long, LegalEntityParticipant>> findByRegistrationCode(String registrationCode) {
        return legalEntityParticipantEntityRepository.findByRegistrationCode(registrationCode)
                .map(
                        legalEntityParticipantEntity -> Pair
                                .of(legalEntityParticipantEntity.getId(), toDomain(legalEntityParticipantEntity)));
    }

    @Override
    public LegalEntityParticipant get(Long id) {
        LegalEntityParticipantEntity legalEntityParticipantEntity = legalEntityParticipantEntityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No LegalEntityParticipantEntity found: " + id));
        return toDomain(legalEntityParticipantEntity);
    }

    private static LegalEntityParticipant toDomain(LegalEntityParticipantEntity legalEntityParticipantEntity) {
        return LegalEntityParticipant.builder()
                .name(legalEntityParticipantEntity.getName())
                .registrationCode(legalEntityParticipantEntity.getRegistrationCode())
                .participantCount(legalEntityParticipantEntity.getParticipantCount())
                .paymentTypeId(legalEntityParticipantEntity.getPaymentType().getId())
                .additionalInformation(legalEntityParticipantEntity.getAdditionalInformation())
                .build();
    }

    @Override
    @Transactional
    public Long create(LegalEntityParticipant legalEntityParticipant) {
        LegalEntityParticipantEntity legalEntityParticipantEntity = toEntity(legalEntityParticipant);
        PaymentTypeEntity paymentType = resolvePaymentType(legalEntityParticipant.getPaymentTypeId());
        legalEntityParticipantEntity.setPaymentType(paymentType);
        LegalEntityParticipantEntity newLegalEntityParticipantEntity = legalEntityParticipantEntityRepository
                .save(legalEntityParticipantEntity);
        legalEntityParticipantEntityRepository.flush();
        return newLegalEntityParticipantEntity.getId();
    }

    private static LegalEntityParticipantEntity toEntity(LegalEntityParticipant legalEntityParticipant) {
        return LegalEntityParticipantEntity.builder()
                .name(legalEntityParticipant.getName())
                .registrationCode(legalEntityParticipant.getRegistrationCode())
                .participantCount(legalEntityParticipant.getParticipantCount())
                .additionalInformation(legalEntityParticipant.getAdditionalInformation())
                .build();
    }

    @Override
    @Transactional
    public void modify(Long id, LegalEntityParticipant legalEntityParticipant) {
        LegalEntityParticipantEntity legalEntityParticipantEntity = legalEntityParticipantEntityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No LegalEntityParticipantEntity found: " + id));
        if (!legalEntityParticipantEntity.getName().equals(legalEntityParticipant.getName())) {
            legalEntityParticipantEntity.setName(legalEntityParticipant.getName());
        }
        if (!legalEntityParticipantEntity.getRegistrationCode().equals(legalEntityParticipant.getRegistrationCode())) {
            legalEntityParticipantEntity.setRegistrationCode(legalEntityParticipant.getRegistrationCode());
        }
        if (!legalEntityParticipantEntity.getParticipantCount().equals(legalEntityParticipant.getParticipantCount())) {
            legalEntityParticipantEntity.setParticipantCount(legalEntityParticipant.getParticipantCount());
        }
        Integer paymentTypeId = legalEntityParticipant.getPaymentTypeId();
        if (!legalEntityParticipantEntity.getPaymentType().getId().equals(paymentTypeId)) {
            PaymentTypeEntity paymentType = resolvePaymentType(paymentTypeId);
            legalEntityParticipantEntity.setPaymentType(paymentType);
        }
        if (!legalEntityParticipantEntity.getAdditionalInformation()
                .equals(legalEntityParticipant.getAdditionalInformation())) {
            legalEntityParticipantEntity.setAdditionalInformation(legalEntityParticipant.getAdditionalInformation());
        }
    }

    private PaymentTypeEntity resolvePaymentType(Integer id) {
        return paymentTypeEntityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No PaymentTypeEntity found: " + id));
    }

}
