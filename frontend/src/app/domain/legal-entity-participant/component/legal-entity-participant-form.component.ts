import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { ErrorService } from '../../../application/core/error.service';
import { AbstractEntityFormComponent } from '../../../application/core/entity-form.component';
import { LegalEntityParticipantEntity, LegalEntityParticipantService } from '../service/legal-entity-participant.service';
import { PaymentTypeService } from '../../shared/service/payment-type.service';
import { PaymentTypeListItem } from '../../../generated/rik-backend';

@Component({
  selector: 'rik-legal-entity-participant-form',
  templateUrl: './legal-entity-participant-form.component.html',
})
export class LegalEntityParticipantFormComponent extends AbstractEntityFormComponent<
  LegalEntityParticipantService,
  LegalEntityParticipantEntity
> {
  protected eventId: number;
  protected paymentTypes: PaymentTypeListItem[] = [];

  public constructor(
    router: Router,
    errorService: ErrorService,
    legalEntityParticipantService: LegalEntityParticipantService,
    private readonly activatedRoute: ActivatedRoute,
    private readonly paymentTypeService: PaymentTypeService,
  ) {
    super(router, errorService, legalEntityParticipantService);
  }

  protected override onInit(): void {
    this.subscribeOnce(this.activatedRoute.params, (params: Params): void => {
      this.eventId = params['id'];
    });
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
        this.entityService.modifyLegalEntityParticipant(legalEntityParticipantEntity.id, legalEntityParticipantEntity),
        (): void => {
          this.successMessage = 'Muudetud';
        },
      );
    } else {
      this.subscribeOnce(this.entityService.addLegalEntityParticipant(this.eventId, legalEntityParticipantEntity), (): void => {
        this.reloadPage();
      });
    }
  }
}
