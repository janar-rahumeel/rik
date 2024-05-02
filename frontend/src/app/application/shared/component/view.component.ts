import { Component, OnInit } from '@angular/core';
import { BehaviorSubject, Observable, Subject } from 'rxjs';
import { AbstractComponent } from '../../core/base.component';
import { Router } from '@angular/router';

@Component({
  selector: 'rik-view',
  templateUrl: './view.component.html',
  styleUrls: ['./view.component.css'],
})
export class ViewComponent extends AbstractComponent implements OnInit {
  protected label: string = '';
  private readonly label$: Observable<string>;
  private readonly labelSubject: Subject<string> = new BehaviorSubject<string>('');

  public constructor(router: Router) {
    super(router);
    this.label$ = this.labelSubject.asObservable();
  }

  public ngOnInit(): void {
    this.subscribe(this.label$, (label: string): void => {
      setTimeout((): void => {
        this.label = label;
      }, 0);
    });
  }

  public getLabelSubject(): Subject<string> {
    return this.labelSubject;
  }
}
