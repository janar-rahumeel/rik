import { Injectable } from '@angular/core';
import { PersonParticipantRepository } from '../../../infrastructure/repository/person-participant.repository';
import {
  AddPersonParticipantRequest,
  ModifyPersonParticipantRequest,
  PersonParticipant,
  PersonParticipantResponse,
} from '../../../generated/rik-backend';
import { map, Observable } from 'rxjs';
import { AbstractEntityPropagatorService } from '../../../application/core/entity-propagator.service';

@Injectable()
export class PersonParticipantService extends AbstractEntityPropagatorService<PersonParticipantEntity> {
  public constructor(private readonly repository: PersonParticipantRepository) {
    super();
  }

  public getPersonParticipant(id: number): Observable<PersonParticipant> {
    return this.repository.get(id);
  }

  public addPersonParticipant(eventId: number, personParticipant: PersonParticipant): Observable<PersonParticipant> {
    const request: AddPersonParticipantRequest = { eventId: eventId, personParticipant };
    return this.repository.add(request).pipe(map((response: PersonParticipantResponse) => response.personParticipant));
  }

  public modifyPersonParticipant(id: number, personParticipant: PersonParticipant): Observable<PersonParticipant> {
    const request: ModifyPersonParticipantRequest = { personParticipant };
    return this.repository.modify(id, request);
  }

  public removePersonParticipant(id: number): Observable<void> {
    return this.repository.remove(id);
  }
}

export interface PersonParticipantEntity extends PersonParticipant {
  id?: number;
}
