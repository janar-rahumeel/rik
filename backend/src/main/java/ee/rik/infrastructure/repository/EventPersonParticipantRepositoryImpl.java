package ee.rik.infrastructure.repository;

import jakarta.persistence.EntityNotFoundException;

import ee.rik.domain.PersonParticipant;
import ee.rik.domain.repository.EventPersonParticipantRepository;
import ee.rik.infrastructure.entity.EventEntity;
import ee.rik.infrastructure.entity.EventPersonParticipantEntity;
import ee.rik.infrastructure.entity.PaymentTypeEntity;
import ee.rik.infrastructure.entity.PersonEntity;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class EventPersonParticipantRepositoryImpl implements EventPersonParticipantRepository {

    private final EventEntityRepository eventEntityRepository;
    private final PersonEntityRepository personEntityRepository;
    private final PaymentTypeEntityRepository paymentTypeEntityRepository;
    private final EventPersonParticipantEntityRepository eventPersonParticipantEntityRepository;

    @Override
    public boolean existsByEventIdAndNationalIdentificationCode(Long eventId, String nationalIdentificationCode) {
        return eventPersonParticipantEntityRepository
                .existsByEventIdAndPersonNationalIdentificationCode(eventId, nationalIdentificationCode);
    }

    @Override
    public PersonParticipant get(Long id) {
        EventPersonParticipantEntity eventPersonParticipantEntity = eventPersonParticipantEntityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No PersonParticipantEntity found: " + id));
        return toDomain(eventPersonParticipantEntity);
    }

    private static PersonParticipant toDomain(EventPersonParticipantEntity eventPersonParticipantEntity) {
        PersonEntity personEntity = eventPersonParticipantEntity.getPerson();
        return PersonParticipant.builder()
                .firstName(personEntity.getFirstName())
                .lastName(personEntity.getLastName())
                .nationalIdentificationCode(personEntity.getNationalIdentificationCode())
                .paymentTypeId(eventPersonParticipantEntity.getPaymentType().getId())
                .additionalInformation(eventPersonParticipantEntity.getAdditionalInformation())
                .build();
    }

    @Override
    @Transactional
    public Long create(Long eventId, Long personId, PersonParticipant personParticipant) { // TODO 2 fields!?!?
        EventEntity eventEntity = eventEntityRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("No EventEntity found: " + eventId));
        PersonEntity personEntity = personEntityRepository.findById(personId)
                .orElseThrow(() -> new EntityNotFoundException("No PersonEntity found: " + personId));
        PaymentTypeEntity paymentType = resolvePaymentType(personParticipant.getPaymentTypeId());
        EventPersonParticipantEntity eventPersonParticipantEntity = EventPersonParticipantEntity.builder()
                .event(eventEntity)
                .person(personEntity)
                .paymentType(paymentType)
                .additionalInformation(personParticipant.getAdditionalInformation())
                .build();
        return eventPersonParticipantEntityRepository.save(eventPersonParticipantEntity).getId();
    }

    @Override
    @Transactional
    public void modify(Long id, PersonParticipant personParticipant) { // TODO 2 field only?!?!?
        EventPersonParticipantEntity eventPersonParticipantEntity = eventPersonParticipantEntityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No EventPersonParticipantEntity found: " + id));
        Integer paymentTypeId = personParticipant.getPaymentTypeId();
        if (!eventPersonParticipantEntity.getPaymentType().getId().equals(paymentTypeId)) {
            PaymentTypeEntity paymentType = resolvePaymentType(paymentTypeId);
            eventPersonParticipantEntity.setPaymentType(paymentType);
        }
        if (!eventPersonParticipantEntity.getAdditionalInformation().equals(personParticipant.getAdditionalInformation())) {
            eventPersonParticipantEntity.setAdditionalInformation(personParticipant.getAdditionalInformation());
        }
    }

    private PaymentTypeEntity resolvePaymentType(Integer id) {
        return paymentTypeEntityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No PaymentTypeEntity found: " + id));
    }

    @Override
    @Transactional
    public void remove(Long id) {
        eventPersonParticipantEntityRepository.findById(id);
    }

}
