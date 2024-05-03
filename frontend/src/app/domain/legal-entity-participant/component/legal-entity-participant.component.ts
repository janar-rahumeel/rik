import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { LegalEntityParticipant } from '../../../generated/rik-backend';
import { AbstractComponent } from '../../../application/core/base.component';
import { LegalEntityParticipantEntity, LegalEntityParticipantService } from '../service/legal-entity-participant.service';
import { ViewService } from '../../../application/shared/service/view.service';

@Component({
  selector: 'rik-legal-entity-participant',
  templateUrl: './legal-entity-participant.component.html',
})
export class LegalEntityParticipantComponent extends AbstractComponent implements OnInit {
  public constructor(
    router: Router,
    private readonly activatedRoute: ActivatedRoute,
    private readonly viewService: ViewService,
    private readonly legalEntityParticipantService: LegalEntityParticipantService,
  ) {
    super(router);
  }

  public ngOnInit(): void {
    this.viewService.getLabelSubject().next('Osavõtja info');
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
