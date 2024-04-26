import {Injectable} from "@angular/core";
import {map, Observable} from "rxjs";
import {EventRepository} from "../repository/event.repository";
import {
  AddPersonParticipantRequest,
  CreateEventRequest,
  Event,
  EventParticipant,
  EventParticipantsResponse,
  ListEvent,
  ListEventsRequest,
  PersonParticipant,
  PersonParticipantResponse
} from "../generated/rik-backend";

@Injectable({providedIn: "root"})
export class EventService {

  public constructor(private readonly repository: EventRepository) {
  }

  public listNewEvents(): Observable<ListEvent[]> {
    let request: ListEventsRequest = {'newEvents': true};
    return this.repository.list(request);
  }

  public listOldEvents(): Observable<ListEvent[]> {
    let request: ListEventsRequest = {'newEvents': false};
    return this.repository.list(request);
  }

  public getEvent(id: number): Observable<Event> {
    return this.repository.get(id);
  }

  public createEvent(event: Event): Observable<Event> {
    let request: CreateEventRequest = {event};
    return this.repository.create(request);
  }

  public getParticipants(id: number): Observable<EventParticipant[]> {
    return this.repository.getParticipants(id).pipe(map((response: EventParticipantsResponse) => response.participants));
  }

  public addPersonParticipant(id: number, personParticipant: PersonParticipant): Observable<PersonParticipant> {
    let request: AddPersonParticipantRequest = {personParticipant};
    return this.repository.addPersonParticipant(id, request).pipe(map((response: PersonParticipantResponse) => response.personParticipant));
  }

}
