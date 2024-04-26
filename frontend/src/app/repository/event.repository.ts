import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {catchError, map, Observable, of} from "rxjs";
import {environment} from "../../environments/environment";
import {CreateEventRequest, ListEvent, ListEventsRequest, ListEventsResponse} from "../generated/rik-backend";

@Injectable({providedIn: "root"})
export class EventRepository {

  public constructor(private readonly httpClient: HttpClient) {
  }

  public list(request: ListEventsRequest): Observable<ListEvent[]> {
    return this.httpClient.post<ListEventsResponse>(
      `${environment.apiUrl}/events/list`,
      request
    ).pipe(map((response: ListEventsResponse) => response.events), catchError(() => of([])));
  }

  public create(request: CreateEventRequest): Observable<void> {
    return this.httpClient.post<void>(
      `${environment.apiUrl}/events`,
      request
    );
  }

}
