package ee.rik.infrastructure.repository;

import jakarta.persistence.EntityNotFoundException;

import ee.rik.domain.LegalEntityParticipant;
import ee.rik.domain.repository.EventLegalEntityParticipantRepository;
import ee.rik.infrastructure.entity.EventEntity;
import ee.rik.infrastructure.entity.EventLegalEntityParticipantEntity;
import ee.rik.infrastructure.entity.LegalEntityEntity;
import ee.rik.infrastructure.entity.PaymentTypeEntity;
import ee.rik.infrastructure.repository.entity.EventEntityRepository;
import ee.rik.infrastructure.repository.entity.EventLegalEntityParticipantEntityRepository;
import ee.rik.infrastructure.repository.entity.LegalEntityEntityRepository;
import ee.rik.infrastructure.repository.entity.PaymentTypeEntityRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EventEventLegalEntityParticipantRepositoryImpl implements EventLegalEntityParticipantRepository {

    private final EventEntityRepository eventEntityRepository;
    private final LegalEntityEntityRepository legalEntityEntityRepository;
    private final EventLegalEntityParticipantEntityRepository eventLegalEntityParticipantEntityRepository;
    private final PaymentTypeEntityRepository paymentTypeEntityRepository;

    @Override
    public boolean existsByEventIdAndRegistrationCode(Long eventId, String registrationCode) {
        return eventLegalEntityParticipantEntityRepository
                .existsByEventIdAndLegalEntityRegistrationCode(eventId, registrationCode);
    }

    @Override
    public LegalEntityParticipant get(Long id) {
        EventLegalEntityParticipantEntity legalEntityParticipantEntity = eventLegalEntityParticipantEntityRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No LegalEntityParticipantEntity found: " + id));
        return toDomain(legalEntityParticipantEntity);
    }

    private static LegalEntityParticipant toDomain(EventLegalEntityParticipantEntity legalEntityParticipantEntity) {
        LegalEntityEntity entityLegalEntity = legalEntityParticipantEntity.getLegalEntity();
        return LegalEntityParticipant.builder()
                .name(entityLegalEntity.getName())
                .registrationCode(entityLegalEntity.getRegistrationCode())
                .participantCount(legalEntityParticipantEntity.getParticipantCount())
                .paymentTypeId(legalEntityParticipantEntity.getPaymentType().getId())
                .additionalInformation(legalEntityParticipantEntity.getAdditionalInformation())
                .build();
    }

    @Override
    @Transactional
    public Long create(
            Long eventId,
            Long legalEntityId,
            Integer paymentTypeId,
            Integer participantCount,
            String additionalInformation) {
        EventEntity event = eventEntityRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("No EventEntity found: " + eventId));
        LegalEntityEntity legalEntity = legalEntityEntityRepository.findById(legalEntityId)
                .orElseThrow(() -> new EntityNotFoundException("No LegalEntityEntity found: " + legalEntityId));
        PaymentTypeEntity paymentType = resolvePaymentType(paymentTypeId);
        EventLegalEntityParticipantEntity eventLegalEntityParticipantEntity = EventLegalEntityParticipantEntity.builder()
                .event(event)
                .legalEntity(legalEntity)
                .paymentType(paymentType)
                .participantCount(participantCount)
                .additionalInformation(additionalInformation)
                .build();
        return eventLegalEntityParticipantEntityRepository.save(eventLegalEntityParticipantEntity).getId();
    }

    @Override
    @Transactional
    public void modify(Long id, Integer paymentTypeId, Integer participantCount, String additionalInformation) {
        EventLegalEntityParticipantEntity eventLegalEntityParticipantEntity = eventLegalEntityParticipantEntityRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No EventLegalEntityParticipantEntity found: " + id));
        if (!eventLegalEntityParticipantEntity.getPaymentType().getId().equals(paymentTypeId)) {
            PaymentTypeEntity paymentType = resolvePaymentType(paymentTypeId);
            eventLegalEntityParticipantEntity.setPaymentType(paymentType);
        }
        if (!eventLegalEntityParticipantEntity.getParticipantCount().equals(participantCount)) {
            eventLegalEntityParticipantEntity.setParticipantCount(participantCount);
        }
        if (!eventLegalEntityParticipantEntity.getAdditionalInformation().equals(additionalInformation)) {
            eventLegalEntityParticipantEntity.setAdditionalInformation(additionalInformation);
        }
    }

    private PaymentTypeEntity resolvePaymentType(Integer id) {
        return paymentTypeEntityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No PaymentTypeEntity found: " + id));
    }

    @Override
    @Transactional
    public void remove(Long id) {
        eventLegalEntityParticipantEntityRepository.deleteById(id);
    }

}
