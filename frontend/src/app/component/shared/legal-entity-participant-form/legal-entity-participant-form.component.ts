import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { LegalEntityParticipant, PaymentTypeListItem } from '../../../generated/rik-backend';
import { ErrorService } from '../../../service/error.service';
import { AbstractEntityFormComponent } from '../../entity-form.component';
import { EventService } from '../../../service/event.service';
import { Subject } from 'rxjs';
import { LegalEntityParticipantService } from '../../../service/legal-entity-participant.service';
import { PaymentTypeService } from '../../../service/payment-type.service';

@Component({
  selector: 'rik-legal-entity-participant-form',
  templateUrl: './legal-entity-participant-form.component.html',
})
export class LegalEntityParticipantFormComponent extends AbstractEntityFormComponent<LegalEntityParticipantEntity> {
  protected eventId: number;
  protected paymentTypes: PaymentTypeListItem[] = [];

  constructor(
    router: Router,
    errorService: ErrorService,
    private readonly activatedRoute: ActivatedRoute,
    private readonly paymentTypeService: PaymentTypeService,
    private readonly legalEntityParticipantService: LegalEntityParticipantService,
    private readonly eventService: EventService,
  ) {
    super(router, errorService);
  }

  public getEntitySubject(): Subject<LegalEntityParticipantEntity> {
    return this.entitySubject;
  }

  protected override onInit(): void {
    this.eventId = this.activatedRoute.snapshot.params['id'];
    this.subscribeOnce(this.paymentTypeService.listPaymentTypes(), (paymentTypes: PaymentTypeListItem[]): void => {
      this.paymentTypes = paymentTypes;
    });
  }

  protected override getForm(): FormGroup {
    return new FormGroup({
      id: new FormControl(undefined),
      name: new FormControl(undefined, Validators.required),
      registrationCode: new FormControl(undefined, Validators.required),
      participantCount: new FormControl(undefined, Validators.required),
      paymentTypeId: new FormControl(undefined, Validators.required),
      additionalInformation: new FormControl(undefined),
    });
  }

  protected override onSubmit(legalEntityParticipantEntity: LegalEntityParticipantEntity): void {
    if (legalEntityParticipantEntity.id) {
      this.subscribeOnce(
        this.legalEntityParticipantService.modifyLegalEntityParticipant(legalEntityParticipantEntity.id, legalEntityParticipantEntity),
        (): void => {
          this.successMessage = 'Muudetud';
        },
      );
    } else {
      this.subscribeOnce(this.eventService.addLegalEntityParticipant(this.eventId, legalEntityParticipantEntity), (): void => {
        this.reloadPage();
      });
    }
  }
}

export interface LegalEntityParticipantEntity extends LegalEntityParticipant {
  id?: number;
}
