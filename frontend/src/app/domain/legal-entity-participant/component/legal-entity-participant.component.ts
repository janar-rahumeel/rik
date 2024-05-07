import { Component, inject, OnInit } from '@angular/core';
import { ActivatedRoute, ActivatedRouteSnapshot, ResolveFn, Router, RouterStateSnapshot } from '@angular/router';
import { LegalEntityParticipant } from '../../../generated/rik-backend';
import { AbstractComponent } from '../../../application/core/base.component';
import { LegalEntityParticipantEntity, LegalEntityParticipantService } from '../service/legal-entity-participant.service';
import { ViewService } from '../../../application/shared/service/view.service';
import { Observable } from 'rxjs';

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
    const snapshot: ActivatedRouteSnapshot = this.activatedRoute.snapshot;
    const legalEntityParticipantEntity: LegalEntityParticipantEntity = snapshot.data['entity'] as LegalEntityParticipantEntity;
    legalEntityParticipantEntity.id = snapshot.params['id'];
    this.legalEntityParticipantService.propagate(legalEntityParticipantEntity);
  }
}
/* eslint-disable @typescript-eslint/no-unused-vars */
export const resolveLegalEntityParticipant: ResolveFn<LegalEntityParticipant> = (
  route: ActivatedRouteSnapshot,
  state: RouterStateSnapshot,
): Observable<LegalEntityParticipant> => {
  const legalEntityParticipantService: LegalEntityParticipantService = inject(LegalEntityParticipantService);
  return legalEntityParticipantService.getLegalEntityParticipant(route.params['id']);
};
