import {Component} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {LegalEntityParticipant} from "../../../generated/rik-backend";
import {ErrorService} from "../../../service/error.service";
import {AbstractEntityFormComponent} from "../../entity-form.component";
import {EventService} from "../../../service/event.service";
import {Subject} from "rxjs";
import {LegalEntityParticipantService} from "../../../service/legal-entity-participant.service";

@Component({
  selector: 'legal-entity-participant-form',
  templateUrl: './legal-entity-participant-form.component.html'
})
export class LegalEntityParticipantFormComponent extends AbstractEntityFormComponent<LegalEntityParticipantEntity> {

  protected eventId: number;

  constructor(router: Router,
              errorService: ErrorService,
              private readonly activatedRoute: ActivatedRoute,
              private readonly legalEntityParticipantService: LegalEntityParticipantService,
              private readonly eventService: EventService) {
    super(router, errorService);
  }

  public getEntitySubject(): Subject<LegalEntityParticipantEntity> {
    return this.entitySubject;
  }

  protected override onInit(): void {
    this.eventId = this.activatedRoute.snapshot.params['id'];
  }

  protected override getForm(): FormGroup {
    return new FormGroup({
      id: new FormControl(undefined),
      name: new FormControl(undefined, Validators.required),
      registrationCode: new FormControl(undefined, Validators.required),
      participantCount: new FormControl(undefined, Validators.required),
      paymentTypeId: new FormControl(undefined, Validators.required),
      additionalInformation: new FormControl(undefined)
    });
  }

  protected override onSubmit(legalEntityParticipantEntity: LegalEntityParticipantEntity): void {
    if (legalEntityParticipantEntity.id) {
      this.subscribeOnce(this.legalEntityParticipantService.modifyLegalEntityParticipant(legalEntityParticipantEntity.id, legalEntityParticipantEntity), ignored => {
        this.successMessage = 'Muudetud';
      });
    } else {
      this.subscribeOnce(this.eventService.addLegalEntityParticipant(this.eventId, legalEntityParticipantEntity), ignored => {
        this.reloadPage();
      });
    }
  }

}

export interface LegalEntityParticipantEntity extends LegalEntityParticipant {
  id?: number;
}
