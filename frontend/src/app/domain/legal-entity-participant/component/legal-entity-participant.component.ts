import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { LegalEntityParticipant } from '../../../generated/rik-backend';
import { ViewComponent } from '../../../application/shared/component/view.component';
import { AbstractComponent } from '../../../application/core/base.component';
import { LegalEntityParticipantEntity, LegalEntityParticipantService } from '../service/legal-entity-participant.service';

@Component({
  selector: 'rik-legal-entity-participant',
  templateUrl: './legal-entity-participant.component.html',
})
export class LegalEntityParticipantComponent extends AbstractComponent implements OnInit {
  public constructor(
    router: Router,
    private readonly view: ViewComponent,
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
        this.legalEntityParticipantService.propagate(legalEntityParticipantEntity);
      },
    );
  }
}
