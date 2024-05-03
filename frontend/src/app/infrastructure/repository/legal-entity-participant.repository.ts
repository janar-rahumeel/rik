import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {
  AddLegalEntityParticipantRequest,
  LegalEntityParticipant,
  LegalEntityParticipantResponse,
  ModifyLegalEntityParticipantRequest,
} from '../../generated/rik-backend';
import { environment } from '../../../environments/environment';

@Injectable()
export class LegalEntityParticipantRepository {
  public constructor(private readonly httpClient: HttpClient) {}

  public get(id: number): Observable<LegalEntityParticipant> {
    return this.httpClient.get<LegalEntityParticipant>(`${environment.apiUrl}/legal-entity-participants/${id}`);
  }

  public add(request: AddLegalEntityParticipantRequest): Observable<LegalEntityParticipantResponse> {
    return this.httpClient.post<LegalEntityParticipantResponse>(`${environment.apiUrl}/legal-entity-participants/`, request);
  }

  public modify(id: number, request: ModifyLegalEntityParticipantRequest): Observable<LegalEntityParticipant> {
    return this.httpClient.put<LegalEntityParticipant>(`${environment.apiUrl}/legal-entity-participants/${id}`, request);
  }

  public remove(id: number): Observable<void> {
    return this.httpClient.delete<void>(`${environment.apiUrl}/legal-entity-participants/${id}`);
  }
}
