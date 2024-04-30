import { Component, OnInit, Optional, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { LegalEntityParticipant } from '../../generated/rik-backend';
import { ViewComponent } from '../shared/view/view.component';
import { AbstractComponent } from '../base.component';
import {
  LegalEntityParticipantEntity,
  LegalEntityParticipantFormComponent,
} from '../shared/legal-entity-participant-form/legal-entity-participant-form.component';
import { LegalEntityParticipantService } from '../../service/legal-entity-participant.service';

@Component({
  selector: 'rik-legal-entity-participant',
  templateUrl: './legal-entity-participant.component.html',
})
export class LegalEntityParticipantComponent extends AbstractComponent implements OnInit {
  @ViewChild(LegalEntityParticipantFormComponent)
  private formComponent: LegalEntityParticipantFormComponent;

  public constructor(
    router: Router,
    @Optional() private readonly view: ViewComponent,
    private readonly activatedRoute: ActivatedRoute,
    private readonly legalEntityParticipantService: LegalEntityParticipantService,
  ) {
    super(router);
  }

  public ngOnInit(): void {
    this.view.getLabelSubject().next('Osavõtja info');
    const legalEntityParticipantId: number = this.activatedRoute.snapshot.params['id'];
    this.subscribeOnce(
      this.legalEntityParticipantService.getLegalEntityParticipant(legalEntityParticipantId),
      (legalEntityParticipant: LegalEntityParticipant): void => {
        const legalEntityParticipantEntity: LegalEntityParticipantEntity = {
          id: legalEntityParticipantId,
          ...legalEntityParticipant,
        };
        this.formComponent.getEntitySubject().next(legalEntityParticipantEntity);
      },
    );
  }
}
