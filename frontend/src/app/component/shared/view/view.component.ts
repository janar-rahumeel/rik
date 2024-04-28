import {Component, OnInit} from '@angular/core';
import {Observable, ReplaySubject, Subject} from "rxjs";
import {AbstractComponent} from "../../base.component";
import {Router} from "@angular/router";

@Component({
  selector: 'view',
  templateUrl: './view.component.html',
  styleUrls: ['./view.component.css']
})
export class ViewComponent extends AbstractComponent implements OnInit {

  protected label: string;
  private label$: Observable<string>;
  private labelSubject: Subject<string> = new ReplaySubject<string>();


  public constructor(router: Router) {
    super(router);
  }

  public ngOnInit(): void {
    this.label$ = this.labelSubject.asObservable();
    this.subscribe(this.label$, (label: string): void => {
      this.label = label;
    });
  }

  public getLabelSubject(): Subject<string> {
    return this.labelSubject;
  }

}