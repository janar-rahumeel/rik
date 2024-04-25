package ee.rik.infrastructure.repository;

import jakarta.persistence.EntityNotFoundException;

import ee.rik.domain.PersonParticipant;
import ee.rik.domain.repository.PersonParticipantRepository;
import ee.rik.infrastructure.entity.PaymentTypeEntity;
import ee.rik.infrastructure.entity.PersonParticipantEntity;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PersonParticipantRepositoryImpl implements PersonParticipantRepository {

    private final PersonParticipantEntityRepository personParticipantEntityRepository;
    private final PaymentTypeEntityRepository paymentTypeEntityRepository;

    @Override
    public PersonParticipant get(Long id) {
        PersonParticipantEntity personParticipantEntity = personParticipantEntityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No PersonParticipantEntity found: " + id));
        return PersonParticipant.builder()
                .firstName(personParticipantEntity.getFirstName())
                .lastName(personParticipantEntity.getLastName())
                .nationalIdentificationCode(personParticipantEntity.getNationalIdentificationCode())
                .paymentTypeId(personParticipantEntity.getPaymentType().getId())
                .additionalInformation(personParticipantEntity.getAdditionalInformation())
                .build();
    }

    @Override
    @Transactional
    public Long create(PersonParticipant personParticipant) {
        PersonParticipantEntity personParticipantEntity = toEntity(personParticipant);
        PaymentTypeEntity paymentType = resolvePaymentType(personParticipant.getPaymentTypeId());
        personParticipantEntity.setPaymentType(paymentType);
        return personParticipantEntityRepository.save(personParticipantEntity).getId();
    }

    private static PersonParticipantEntity toEntity(PersonParticipant personParticipant) {
        return PersonParticipantEntity.builder()
                .firstName(personParticipant.getFirstName())
                .lastName(personParticipant.getLastName())
                .nationalIdentificationCode(personParticipant.getNationalIdentificationCode())
                .additionalInformation(personParticipant.getAdditionalInformation())
                .build();
    }

    @Override
    @Transactional
    public void modify(Long id, PersonParticipant personParticipant) {
        PersonParticipantEntity personParticipantEntity = personParticipantEntityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No PersonParticipantEntity found: " + id));
        if (!personParticipantEntity.getFirstName().equals(personParticipant.getFirstName())) {
            personParticipantEntity.setFirstName(personParticipant.getFirstName());
        }
        if (!personParticipantEntity.getLastName().equals(personParticipant.getLastName())) {
            personParticipantEntity.setLastName(personParticipant.getLastName());
        }
        if (!personParticipantEntity.getNationalIdentificationCode()
                .equals(personParticipant.getNationalIdentificationCode())) {
            personParticipantEntity.setNationalIdentificationCode(personParticipant.getNationalIdentificationCode());
        }
        Integer paymentTypeId = personParticipant.getPaymentTypeId();
        if (!personParticipantEntity.getPaymentType().getId().equals(paymentTypeId)) {
            PaymentTypeEntity paymentType = resolvePaymentType(paymentTypeId);
            personParticipantEntity.setPaymentType(paymentType);
        }
        if (!personParticipantEntity.getAdditionalInformation().equals(personParticipant.getAdditionalInformation())) {
            personParticipantEntity.setAdditionalInformation(personParticipant.getAdditionalInformation());
        }
    }

    private PaymentTypeEntity resolvePaymentType(Integer id) {
        return paymentTypeEntityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No PaymentTypeEntity found: " + id));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        personParticipantEntityRepository.deleteById(id);
    }

}
