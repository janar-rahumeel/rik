import {Component, OnInit} from '@angular/core';
import {BehaviorSubject, Observable, Subject} from "rxjs";
import {AbstractComponent} from "../../base.component";
import {Router} from "@angular/router";

@Component({
  selector: 'rik-view',
  templateUrl: './view.component.html',
  styleUrls: ['./view.component.css']
})
export class ViewComponent extends AbstractComponent implements OnInit {

  protected label$: Observable<string>;
  private labelSubject: Subject<string> = new BehaviorSubject<string>('');


  public constructor(router: Router) {
    super(router);
  }

  public ngOnInit(): void {
    this.label$ = this.labelSubject.asObservable();
  }

  public getLabelSubject(): Subject<string> {
    return this.labelSubject;
  }

}
