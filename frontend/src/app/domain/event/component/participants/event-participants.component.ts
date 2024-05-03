import { Component, OnInit } from '@angular/core';
import { EventService } from '../../service/event.service';
import { Event, EventParticipant } from '../../../../generated/rik-backend';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { AbstractComponent } from '../../../../application/core/base.component';
import { PersonParticipantService } from '../../../person-participant/service/person-participant.service';
import { LegalEntityParticipantService } from '../../../legal-entity-participant/service/legal-entity-participant.service';
import { forkJoin } from 'rxjs';
import { ViewService } from '../../../../application/shared/service/view.service';

@Component({
  selector: 'rik-list-event-participants',
  templateUrl: './event-participants.component.html',
  styleUrls: ['./event-participants.component.css'],
})
export class EventParticipantsComponent extends AbstractComponent implements OnInit {
  protected eventId: number;
  protected event?: Event;
  protected personSelectionEnabled: boolean = true;
  protected eventParticipants: EventParticipant[];

  public constructor(
    router: Router,
    private readonly activatedRoute: ActivatedRoute,
    private readonly viewService: ViewService,
    private readonly eventService: EventService,
    private readonly personParticipantService: PersonParticipantService,
    private readonly legalEntityParticipantService: LegalEntityParticipantService,
  ) {
    super(router);
  }

  public ngOnInit(): void {
    this.viewService.getLabelSubject().next('Osavõtjad');
    this.subscribeOnce(this.activatedRoute.params, (params: Params): void => {
      this.eventId = params['id'];
      const observables = {
        event: this.eventService.getEvent(this.eventId),
        eventParticipants: this.eventService.getParticipants(this.eventId),
      };
      this.subscribeOnce(forkJoin(observables), (response): void => {
        this.event = response.event;
        this.eventParticipants = response.eventParticipants;
      });
    });
  }

  protected onParticipantShowButtonClick(hybridId: string): void {
    if (hybridId.startsWith('PP-')) {
      const personParticipantId: number = Number(hybridId.replace('PP-', ''));
      this.router.navigate(['/person-participant/', personParticipantId]);
    }
    if (hybridId.startsWith('LEP-')) {
      const legalEntityParticipantId: number = Number(hybridId.replace('LEP-', ''));
      this.router.navigate(['/legal-entity-participant/', legalEntityParticipantId]);
    }
  }

  protected onParticipantDeleteButtonClick(hybridId: string): void {
    if (hybridId.startsWith('PP-')) {
      const personParticipantId: number = Number(hybridId.replace('PP-', ''));
      this.subscribeOnce(this.personParticipantService.removePersonParticipant(personParticipantId), (): void => {
        this.reloadPage();
      });
    }
    if (hybridId.startsWith('LEP-')) {
      const legalEntityParticipantId: number = Number(hybridId.replace('LEP-', ''));
      this.subscribeOnce(this.legalEntityParticipantService.removeLegalEntityParticipant(legalEntityParticipantId), (): void => {
        this.reloadPage();
      });
    }
  }

  protected onPersonSelectionButtonClick(): void {
    this.personSelectionEnabled = true;
  }

  protected onLegalEntitySelectionButtonClick(): void {
    this.personSelectionEnabled = false;
  }
}
