import { Component, OnInit, Optional } from '@angular/core';
import { EventService } from '../../../service/event.service';
import { Event, EventParticipant } from '../../../generated/rik-backend';
import { ActivatedRoute, Router } from '@angular/router';
import { ViewComponent } from '../../shared/view/view.component';
import { AbstractComponent } from '../../base.component';
import { PersonParticipantService } from '../../../service/person-participant.service';
import { LegalEntityParticipantService } from '../../../service/legal-entity-participant.service';

@Component({
  selector: 'rik-list-event-participants',
  templateUrl: './list-event-participants.component.html',
  styleUrls: ['./list-event-participants.component.css'],
})
export class ListEventParticipantsComponent
  extends AbstractComponent
  implements OnInit
{
  protected eventId: number;
  protected event?: Event;
  protected personSelectionEnabled: boolean = true;
  protected eventParticipants: EventParticipant[];

  public constructor(
    router: Router,
    @Optional() private readonly view: ViewComponent,
    private readonly activatedRoute: ActivatedRoute,
    private readonly eventService: EventService,
    private readonly personParticipantService: PersonParticipantService,
    private readonly legalEntityParticipantService: LegalEntityParticipantService,
  ) {
    super(router);
  }

  public ngOnInit(): void {
    this.view.getLabelSubject().next('Osavõtjad');
    this.eventId = this.activatedRoute.snapshot.params['id'];
    this.subscribeOnce(
      this.eventService.getEvent(this.eventId),
      (event: Event): void => {
        this.event = event;
      },
    );
    this.subscribeOnce(
      this.eventService.getParticipants(this.eventId),
      (eventParticipants: EventParticipant[]): void => {
        this.eventParticipants = eventParticipants;
      },
    );
  }

  protected onParticipantShowButtonClick(hybridId: string): void {
    if (hybridId.startsWith('PP-')) {
      const personParticipantId: number = Number(hybridId.replace('PP-', ''));
      this.router.navigate(['/view/person-participant/', personParticipantId]);
    }
    if (hybridId.startsWith('LEP-')) {
      const legalEntityParticipantId: number = Number(
        hybridId.replace('LEP-', ''),
      );
      this.router.navigate([
        '/view/legal-entity-participant/',
        legalEntityParticipantId,
      ]);
    }
  }

  protected onParticipantDeleteButtonClick(hybridId: string): void {
    if (hybridId.startsWith('PP-')) {
      const personParticipantId: number = Number(hybridId.replace('PP-', ''));
      this.subscribeOnce(
        this.personParticipantService.removePersonParticipant(
          personParticipantId,
        ),
        (): void => {
          this.reloadPage();
        },
      );
    }
    if (hybridId.startsWith('LEP-')) {
      const legalEntityParticipantId: number = Number(
        hybridId.replace('LEP-', ''),
      );
      this.subscribeOnce(
        this.legalEntityParticipantService.removeLegalEntityParticipant(
          legalEntityParticipantId,
        ),
        (): void => {
          this.reloadPage();
        },
      );
    }
  }

  protected onPersonSelectionButtonClick(): void {
    this.personSelectionEnabled = true;
  }

  protected onLegalEntitySelectionButtonClick(): void {
    this.personSelectionEnabled = false;
  }
}
