import {Component, OnInit, Optional} from '@angular/core';
import {EventService} from "../../../service/event.service";
import {Event, EventParticipant} from "../../../generated/rik-backend";
import {ActivatedRoute, Router} from "@angular/router";
import {ViewComponent} from "../../shared/view/view.component";
import {AbstractComponent} from "../../base.component";

@Component({
  selector: 'list-event-participants',
  templateUrl: './list-event-participants.component.html',
  styleUrls: ['./list-event-participants.component.css']
})
export class ListEventParticipantsComponent extends AbstractComponent implements OnInit {

  protected eventId: number;
  protected event: Event;
  protected personSelectionEnabled: boolean = true;
  protected eventParticipants: EventParticipant[];

  public constructor(router: Router,
                     @Optional() private readonly view: ViewComponent,
                     private readonly activatedRoute: ActivatedRoute,
                     private readonly eventService: EventService) {
    super(router);
  }

  public ngOnInit(): void {
    this.view.getLabelSubject().next('Osavõtjad');
    this.eventId = this.activatedRoute.snapshot.params['id'];
    this.subscribeOnce(this.eventService.getEvent(this.eventId), (event: Event): void => {
      this.event = event;
    });
    this.subscribeOnce(this.eventService.getParticipants(this.eventId), (eventParticipants: EventParticipant[]): void => {
      this.eventParticipants = eventParticipants;
    });
  }

  protected onParticipantShowButtonClick(hybridId: string): void {
    if (hybridId.startsWith('PP-')) {
      let personParticipantId: number = Number(hybridId.replace('PP-', ''));
      this.router.navigate(['/view/person-participant/', personParticipantId]);
    }
    if (hybridId.startsWith('LEP-')) {
      let legalEntityParticipantId: number = Number(hybridId.replace('LEP-', ''));
      this.router.navigate(['/view/legal-entity-participant/', legalEntityParticipantId]);
    }
  }

  protected onParticipantDeleteButtonClick(hybridId: string): void {
    if (hybridId.startsWith('PP-')) {
      let personParticipantId: number = Number(hybridId.replace('PP-', ''));
      this.subscribeOnce(this.eventService.removePersonParticipant(this.eventId, personParticipantId), (): void => {
        this.reloadPage();
      });
    }
    if (hybridId.startsWith('LEP-')) {
      let legalEntityParticipantId: number = Number(hybridId.replace('LEP-', ''));
      this.subscribeOnce(this.eventService.removeLegalEntityParticipant(this.eventId, legalEntityParticipantId), (): void => {
        this.reloadPage();
      });
    }
  }

  protected onPersonSelectionButtonClicked(): void {
    this.personSelectionEnabled = true;
  }

  protected onLegalEntitySelectionButtonClicked(): void {
    this.personSelectionEnabled = false;
  }

}
