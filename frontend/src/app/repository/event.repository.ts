import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";
import {ListEvent, ListEventsRequest} from "../generated/rik-backend";

@Injectable({providedIn: "root"})
export class EventRepository {

  public constructor(private readonly httpClient: HttpClient) {
  }

  public list(request: ListEventsRequest): Observable<ListEvent[]> {
    return this.httpClient.post<ListEvent[]>(
      `${environment.apiUrl}/events/list`,
      request
    );
  }

}
