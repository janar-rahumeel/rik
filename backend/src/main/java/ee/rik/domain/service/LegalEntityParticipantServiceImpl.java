package ee.rik.domain.service;

import ee.rik.domain.LegalEntityParticipant;
import ee.rik.domain.repository.LegalEntityParticipantRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LegalEntityParticipantServiceImpl implements LegalEntityParticipantService {

    private final LegalEntityParticipantRepository legalEntityParticipantRepository;

    @Override
    public Long createLegalEntityParticipant(LegalEntityParticipant legalEntityParticipant) {
        return legalEntityParticipantRepository.create(legalEntityParticipant);
    }

    @Override
    public void modifyLegalEntityParticipant(Long id, LegalEntityParticipant legalEntityParticipant) {
        legalEntityParticipantRepository.modify(id, legalEntityParticipant);
    }

    @Override
    public void deleteLegalEntityParticipant(Long id) {
        legalEntityParticipantRepository.delete(id);
    }

}
