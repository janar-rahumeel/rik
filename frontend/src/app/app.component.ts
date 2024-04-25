import {Component, OnInit} from '@angular/core';
import {ErrorService} from "./service/error.service";
import {debounceTime} from "rxjs";
import {ErrorResponse} from "./generated/rik-backend";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  protected progressing: boolean = false;

  public constructor(private readonly errorService: ErrorService) {
  }

  title = 'crawler';

  public ngOnInit(): void {
    this.errorService.globalErrors$.pipe(debounceTime(2000)).subscribe((error: ErrorResponse): void => {
      let message: string = "[" + error.uuid! + "] " + error.message;
      // TODO alert('TODO: ' + message);
    });
  }

}
