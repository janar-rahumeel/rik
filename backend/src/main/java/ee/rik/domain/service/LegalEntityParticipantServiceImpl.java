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
    public LegalEntityParticipant getLegalEntityParticipant(Long id) {
        return legalEntityParticipantRepository.get(id);
    }

    @Override
    public void modifyLegalEntityParticipant(Long id, LegalEntityParticipant legalEntityParticipant) {
        legalEntityParticipantRepository.modify(id, legalEntityParticipant);
    }

}
