import {Component, OnInit} from '@angular/core';
import {EventService} from "../../service/event.service";
import {ListEvent} from "../../generated/rik-backend";
import {AbstractComponent} from "../base.component";
import {forkJoin} from "rxjs";
import {Router} from "@angular/router";

@Component({
  selector: 'home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent extends AbstractComponent implements OnInit {

  protected newEvents: ListEvent[] = [];
  protected oldEvents: ListEvent[] = [];

  public constructor(router: Router, private readonly eventService: EventService) {
    super(router);
  }

  ngOnInit(): void {
    let observables = {
      newEvents: this.eventService.listNewEvents(),
      oldEvents: this.eventService.listOldEvents()
    };
    this.subscribeOnce(forkJoin(observables), (response): void => {
      this.newEvents = response.newEvents;
      this.oldEvents = response.oldEvents;
    });
  }

}
