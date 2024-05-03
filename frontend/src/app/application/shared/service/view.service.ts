import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, Subject } from 'rxjs';

@Injectable()
export class ViewService {
  private readonly label$: Observable<string>;
  private readonly labelSubject: Subject<string> = new BehaviorSubject<string>('');

  public constructor() {
    this.label$ = this.labelSubject.asObservable();
  }

  public getLabelObservable(): Observable<string> {
    return this.label$;
  }

  public getLabelSubject(): Subject<string> {
    return this.labelSubject;
  }
}
