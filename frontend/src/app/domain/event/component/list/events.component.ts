import { Component, OnInit } from '@angular/core';
import { EventService } from '../../service/event.service';
import { EventListItem } from '../../../../generated/rik-backend';
import { AbstractComponent } from '../../../../application/core/base.component';
import { forkJoin } from 'rxjs';
import { Router } from '@angular/router';

@Component({
  selector: 'rik-home',
  templateUrl: './events.component.html',
  styleUrls: ['./events.component.css'],
})
export class EventsComponent extends AbstractComponent implements OnInit {
  protected newEvents: EventListItem[] = [];
  protected oldEvents: EventListItem[] = [];

  public constructor(
    router: Router,
    private readonly eventService: EventService,
  ) {
    super(router);
  }

  public ngOnInit(): void {
    const observables = {
      newEvents: this.eventService.listNewEvents(),
      oldEvents: this.eventService.listOldEvents(),
    };
    this.subscribeOnce(forkJoin(observables), (response): void => {
      this.newEvents = response.newEvents;
      this.oldEvents = response.oldEvents;
    });
  }

  protected onEventRemoveButtonClick(id: number): void {
    this.subscribeOnce(this.eventService.removeEvent(id), (): void => {
      this.reloadPage(); // TODO doesn't work
    });
  }
}
