package ee.rik.infrastructure.repository;

import java.util.Optional;

import jakarta.persistence.EntityNotFoundException;

import ee.rik.domain.LegalEntity;
import ee.rik.domain.repository.LegalEntityRepository;
import ee.rik.infrastructure.entity.LegalEntityEntity;
import ee.rik.infrastructure.repository.entity.LegalEntityEntityRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LegalEntityRepositoryImpl implements LegalEntityRepository {

    private final LegalEntityEntityRepository legalEntityEntityRepository;

    @Override
    public Optional<Pair<Long, LegalEntity>> findByRegistrationCode(String registrationCode) {
        return legalEntityEntityRepository.findByRegistrationCode(registrationCode)
                .map(legalEntityEntity -> Pair.of(legalEntityEntity.getId(), toDomain(legalEntityEntity)));
    }

    @Override
    public LegalEntity get(Long id) {
        LegalEntityEntity legalEntityEntity = legalEntityEntityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No LegalEntityEntity found: " + id));
        return toDomain(legalEntityEntity);
    }

    private static LegalEntity toDomain(LegalEntityEntity legalEntityEntity) {
        return LegalEntity.builder()
                .name(legalEntityEntity.getName())
                .registrationCode(legalEntityEntity.getRegistrationCode())
                .build();
    }

    @Override
    @Transactional
    public Long create(LegalEntity legalEntity) {
        LegalEntityEntity legalEntityEntity = toEntity(legalEntity);
        return legalEntityEntityRepository.save(legalEntityEntity).getId();
    }

    private static LegalEntityEntity toEntity(LegalEntity legalEntity) {
        return LegalEntityEntity.builder()
                .name(legalEntity.getName())
                .registrationCode(legalEntity.getRegistrationCode())
                .build();
    }

    @Override
    @Transactional
    public void modify(LegalEntity legalEntity) {
        String registrationCode = legalEntity.getRegistrationCode();
        LegalEntityEntity legalEntityEntity = legalEntityEntityRepository.findByRegistrationCode(registrationCode)
                .orElseThrow(() -> new EntityNotFoundException("No LegalEntityEntity found: " + registrationCode));
        if (!legalEntityEntity.getName().equals(legalEntity.getName())) {
            legalEntityEntity.setName(legalEntity.getName());
        }
    }

}
