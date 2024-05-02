import { Observable, ReplaySubject, Subject } from 'rxjs';

export abstract class AbstractEntityPropagatorService<E> {
  public entity$: Observable<E>; // TODO destroy observable!?!?!?
  private entitySubject: Subject<E> = new ReplaySubject<E>(1);

  protected constructor() {
    this.entity$ = this.entitySubject.asObservable();
  }

  public propagate(value: E): void {
    this.entitySubject.next(value);
  }
}
