import { Component } from '@angular/core';
import { ViewComponent } from './application/shared/component/view.component';

@Component({
  selector: 'rik-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  providers: [ViewComponent],
})
export class AppComponent {
  public constructor() {}
}
