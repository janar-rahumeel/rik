import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import {
  AddLegalEntityParticipantRequest,
  AddPersonParticipantRequest,
  CreateEventRequest,
  Event,
  EventParticipantsResponse,
  LegalEntityParticipantResponse,
  ListEventsRequest,
  ListEventsResponse,
  PersonParticipantResponse,
} from '../generated/rik-backend';

@Injectable({ providedIn: 'root' })
export class EventRepository {
  public constructor(private readonly httpClient: HttpClient) {}

  public list(request: ListEventsRequest): Observable<ListEventsResponse> {
    return this.httpClient.post<ListEventsResponse>(
      `${environment.apiUrl}/events/list`,
      request,
    );
  }

  public get(id: number): Observable<Event> {
    return this.httpClient.get<Event>(`${environment.apiUrl}/events/${id}`);
  }

  public create(request: CreateEventRequest): Observable<Event> {
    return this.httpClient.post<Event>(`${environment.apiUrl}/events`, request);
  }

  public remove(id: number): Observable<void> {
    return this.httpClient.delete<void>(`${environment.apiUrl}/events/${id}`);
  }

  public getParticipants(id: number): Observable<EventParticipantsResponse> {
    return this.httpClient.get<EventParticipantsResponse>(
      `${environment.apiUrl}/events/${id}/participants`,
    );
  }

  public addPersonParticipant(
    id: number,
    request: AddPersonParticipantRequest,
  ): Observable<PersonParticipantResponse> {
    return this.httpClient.post<PersonParticipantResponse>(
      `${environment.apiUrl}/events/${id}/participants/person`,
      request,
    );
  }

  public addLegalEntityParticipant(
    id: number,
    request: AddLegalEntityParticipantRequest,
  ): Observable<LegalEntityParticipantResponse> {
    return this.httpClient.post<LegalEntityParticipantResponse>(
      `${environment.apiUrl}/events/${id}/participants/legal-entity`,
      request,
    );
  }
}
