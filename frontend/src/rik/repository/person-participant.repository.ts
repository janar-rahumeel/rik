import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {
  ModifyPersonParticipantRequest,
  PersonParticipant,
} from '../generated/rik-backend';
import { environment } from '../../environments/environment';

@Injectable({ providedIn: 'root' })
export class PersonParticipantRepository {
  public constructor(private readonly httpClient: HttpClient) {}

  public get(id: number): Observable<PersonParticipant> {
    return this.httpClient.get<PersonParticipant>(
      `${environment.apiUrl}/person-participants/${id}`,
    );
  }

  public modify(
    id: number,
    request: ModifyPersonParticipantRequest,
  ): Observable<PersonParticipant> {
    return this.httpClient.put<PersonParticipant>(
      `${environment.apiUrl}/person-participants/${id}`,
      request,
    );
  }

  public remove(id: number): Observable<void> {
    return this.httpClient.delete<void>(
      `${environment.apiUrl}/person-participants/${id}`,
    );
  }
}
