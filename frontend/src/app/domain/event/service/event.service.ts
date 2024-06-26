import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import {
  CreateEventRequest,
  Event,
  EventListItem,
  EventParticipant,
  EventParticipantsResponse,
  ListEventsRequest,
  ListEventsResponse,
} from '../../../generated/rik-backend';
import { AbstractEntityPropagatorService } from '../../../application/core/entity-propagator.service';
import { EventRepository } from '../../../infrastructure/repository/event.repository';

@Injectable()
export class EventService extends AbstractEntityPropagatorService<Event> {
  public constructor(private readonly repository: EventRepository) {
    super();
  }

  public listNewEvents(): Observable<EventListItem[]> {
    const request: ListEventsRequest = { newEvents: true };
    return this.repository.list(request).pipe(map((response: ListEventsResponse) => response.events));
  }

  public listOldEvents(): Observable<EventListItem[]> {
    const request: ListEventsRequest = { newEvents: false };
    return this.repository.list(request).pipe(map((response: ListEventsResponse) => response.events));
  }

  public getEvent(id: number): Observable<Event> {
    return this.repository.get(id);
  }

  public createEvent(event: Event): Observable<Event> {
    const request: CreateEventRequest = { event };
    return this.repository.create(request);
  }

  public removeEvent(id: number): Observable<void> {
    return this.repository.remove(id);
  }

  public getParticipants(id: number): Observable<EventParticipant[]> {
    return this.repository.getParticipants(id).pipe(map((response: EventParticipantsResponse) => response.participants));
  }
}
