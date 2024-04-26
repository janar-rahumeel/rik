import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {EventRepository} from "../repository/event.repository";
import {CreateEventRequest, Event, ListEvent, ListEventsRequest} from "../generated/rik-backend";

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

  public createEvent(event: Event): Observable<void> {
    let request: CreateEventRequest = {event};
    return this.repository.create(request);
  }

}
