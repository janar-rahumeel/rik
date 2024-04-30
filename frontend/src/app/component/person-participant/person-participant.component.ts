import { Component, OnInit, Optional, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PersonParticipant } from '../../generated/rik-backend';
import { PersonParticipantService } from '../../service/person-participant.service';
import { ViewComponent } from '../shared/view/view.component';
import {
  PersonParticipantEntity,
  PersonParticipantFormComponent,
} from '../shared/person-participant-form/person-participant-form.component';
import { AbstractComponent } from '../base.component';

@Component({
  selector: 'rik-person-participant',
  templateUrl: './person-participant.component.html',
})
export class PersonParticipantComponent extends AbstractComponent implements OnInit {
  @ViewChild(PersonParticipantFormComponent)
  private formComponent: PersonParticipantFormComponent;

  public constructor(
    router: Router,
    @Optional() private readonly view: ViewComponent,
    private readonly activatedRoute: ActivatedRoute,
    private readonly personParticipantService: PersonParticipantService,
  ) {
    super(router);
  }

  public ngOnInit(): void {
    this.view.getLabelSubject().next('Osavõtja info');
    const personParticipantId: number = this.activatedRoute.snapshot.params['id'];
    this.subscribeOnce(
      this.personParticipantService.getPersonParticipant(personParticipantId),
      (personParticipant: PersonParticipant): void => {
        const personParticipantEntity: PersonParticipantEntity = {
          id: personParticipantId,
          ...personParticipant,
        };
        this.formComponent.getEntitySubject().next(personParticipantEntity);
      },
    );
  }
}
