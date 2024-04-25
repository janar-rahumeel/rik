import {Observable} from "rxjs";

export interface ListService<R, E> {

  list(request: R): Observable<E[]>

}
