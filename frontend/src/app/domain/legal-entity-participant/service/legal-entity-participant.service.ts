import { Injectable } from '@angular/core';
import {
  AddLegalEntityParticipantRequest,
  LegalEntityParticipant,
  LegalEntityParticipantResponse,
  ModifyLegalEntityParticipantRequest,
} from '../../../generated/rik-backend';
import { map, Observable } from 'rxjs';
import { LegalEntityParticipantRepository } from '../../../infrastructure/repository/legal-entity-participant.repository';
import { AbstractEntityPropagatorService } from '../../../application/core/entity-propagator.service';

@Injectable()
export class LegalEntityParticipantService extends AbstractEntityPropagatorService<LegalEntityParticipantEntity> {
  public constructor(private readonly repository: LegalEntityParticipantRepository) {
    super();
  }

  public getLegalEntityParticipant(id: number): Observable<LegalEntityParticipant> {
    return this.repository.get(id);
  }

  public addLegalEntityParticipant(eventId: number, legalEntityParticipant: LegalEntityParticipant) {
    const request: AddLegalEntityParticipantRequest = {
      eventId: eventId,
      legalEntityParticipant,
    };
    return this.repository.add(request).pipe(map((response: LegalEntityParticipantResponse) => response.legalEntityParticipant));
  }

  public modifyLegalEntityParticipant(id: number, legalEntityParticipant: LegalEntityParticipant): Observable<LegalEntityParticipant> {
    const request: ModifyLegalEntityParticipantRequest = {
      legalEntityParticipant,
    };
    return this.repository.modify(id, request);
  }

  public removeLegalEntityParticipant(id: number): Observable<void> {
    return this.repository.remove(id);
  }
}

export interface LegalEntityParticipantEntity extends LegalEntityParticipant {
  id?: number;
}
