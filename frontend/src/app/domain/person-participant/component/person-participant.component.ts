import { Component, inject, OnInit } from '@angular/core';
import { ActivatedRoute, ActivatedRouteSnapshot, ResolveFn, Router, RouterStateSnapshot } from '@angular/router';
import { PersonParticipant } from '../../../generated/rik-backend';
import { PersonParticipantEntity, PersonParticipantService } from '../service/person-participant.service';
import { AbstractComponent } from '../../../application/core/base.component';
import { ViewService } from '../../../application/shared/service/view.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'rik-person-participant',
  templateUrl: './person-participant.component.html',
})
export class PersonParticipantComponent extends AbstractComponent implements OnInit {
  public constructor(
    router: Router,
    private readonly activatedRoute: ActivatedRoute,
    private readonly viewService: ViewService,
    private readonly personParticipantService: PersonParticipantService,
  ) {
    super(router);
  }

  public ngOnInit(): void {
    this.viewService.getLabelSubject().next('Osavõtja info');
    const snapshot: ActivatedRouteSnapshot = this.activatedRoute.snapshot;
    const personParticipantEntity: PersonParticipantEntity = snapshot.data['entity'] as PersonParticipantEntity;
    personParticipantEntity.id = snapshot.params['id'];
    this.personParticipantService.propagate(personParticipantEntity);
  }
}
/* eslint-disable @typescript-eslint/no-unused-vars */
export const resolvePersonParticipant: ResolveFn<PersonParticipant> = (
  route: ActivatedRouteSnapshot,
  state: RouterStateSnapshot,
): Observable<PersonParticipant> => {
  const personParticipantService: PersonParticipantService = inject(PersonParticipantService);
  return personParticipantService.getPersonParticipant(route.params['id']);
};
