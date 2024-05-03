import { Component, OnInit } from '@angular/core';
import { AbstractComponent } from '../../core/base.component';
import { Router } from '@angular/router';
import { ViewService } from '../service/view.service';

@Component({
  selector: 'rik-view',
  templateUrl: './view.component.html',
  styleUrls: ['./view.component.css'],
})
export class ViewComponent extends AbstractComponent implements OnInit {
  protected label: string = '';

  public constructor(
    router: Router,
    private readonly viewService: ViewService,
  ) {
    super(router);
  }

  public ngOnInit(): void {
    this.subscribe(this.viewService.getLabelObservable(), (label: string): void => {
      this.label = label;
    });
  }
}
