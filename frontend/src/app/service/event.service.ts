import {Injectable} from "@angular/core";
import {map, Observable} from "rxjs";
import {EventRepository} from "../repository/event.repository";
import {
  AddLegalEntityParticipantRequest,
  AddPersonParticipantRequest,
  CreateEventRequest,
  Event,
  EventListItem,
  EventParticipant,
  EventParticipantsResponse,
  LegalEntityParticipant,
  LegalEntityParticipantResponse,
  ListEventsRequest,
  ListEventsResponse,
  PersonParticipant,
  PersonParticipantResponse
} from "../generated/rik-backend";

@Injectable({providedIn: "root"})
export class EventService {

  public constructor(private readonly repository: EventRepository) {
  }

  public listNewEvents(): Observable<EventListItem[]> {
    let request: ListEventsRequest = {'newEvents': true};
    return this.repository.list(request).pipe(map((response: ListEventsResponse) => response.events));
  }

  public listOldEvents(): Observable<EventListItem[]> {
    let request: ListEventsRequest = {'newEvents': false};
    return this.repository.list(request).pipe(map((response: ListEventsResponse) => response.events));
  }

  public getEvent(id: number): Observable<Event> {
    return this.repository.get(id);
  }

  public createEvent(event: Event): Observable<Event> {
    let request: CreateEventRequest = {event};
    return this.repository.create(request);
  }

  public removeEvent(id: number): Observable<void> {
    return this.repository.remove(id);
  }

  public getParticipants(id: number): Observable<EventParticipant[]> {
    return this.repository.getParticipants(id).pipe(map((response: EventParticipantsResponse) => response.participants));
  }

  public addPersonParticipant(id: number, personParticipant: PersonParticipant): Observable<PersonParticipant> {
    let request: AddPersonParticipantRequest = {personParticipant};
    return this.repository.addPersonParticipant(id, request).pipe(map((response: PersonParticipantResponse) => response.personParticipant));
  }

  public removePersonParticipant(id: number, personParticipantId: number): Observable<void> {
    return this.repository.removePersonParticipant(id, personParticipantId);
  }

  public addLegalEntityParticipant(id: number, legalEntityParticipant: LegalEntityParticipant) {
    let request: AddLegalEntityParticipantRequest = {legalEntityParticipant};
    return this.repository.addLegalEntityParticipant(id, request).pipe(map((response: LegalEntityParticipantResponse) => response.legalEntityParticipant));
  }

  public removeLegalEntityParticipant(id: number, legalEntityParticipantId: number): Observable<void> {
    return this.repository.removeLegalEntityParticipant(id, legalEntityParticipantId);
  }

}
