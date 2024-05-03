import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { PersonParticipant } from '../../../generated/rik-backend';
import { PersonParticipantEntity, PersonParticipantService } from '../service/person-participant.service';
import { ViewComponent } from '../../../application/shared/component/view.component';
import { AbstractComponent } from '../../../application/core/base.component';

@Component({
  selector: 'rik-person-participant',
  templateUrl: './person-participant.component.html',
})
export class PersonParticipantComponent extends AbstractComponent implements OnInit {
  public constructor(
    router: Router,
    private readonly view: ViewComponent,
    private readonly activatedRoute: ActivatedRoute,
    private readonly personParticipantService: PersonParticipantService,
  ) {
    super(router);
  }

  public ngOnInit(): void {
    this.view.getLabelSubject().next('Osavõtja info');
    this.subscribeOnce(this.activatedRoute.params, (params: Params): void => {
      const personParticipantId: number = params['id'];
      this.subscribeOnce(
        this.personParticipantService.getPersonParticipant(personParticipantId),
        (personParticipant: PersonParticipant): void => {
          const personParticipantEntity: PersonParticipantEntity = {
            id: personParticipantId,
            ...personParticipant,
          };
          this.personParticipantService.propagate(personParticipantEntity);
        },
      );
    });
  }
}
