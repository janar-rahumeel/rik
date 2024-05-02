import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { PaymentTypeListItem } from '../../../generated/rik-backend';
import { ErrorService } from '../../../application/core/error.service';
import { AbstractEntityFormComponent } from '../../../application/core/entity-form.component';
import { PersonParticipantEntity, PersonParticipantService } from '../service/person-participant.service';
import { PaymentTypeService } from '../../shared/service/payment-type.service';

@Component({
  selector: 'rik-person-participant-form',
  templateUrl: './person-participant-form.component.html',
})
export class PersonParticipantFormComponent extends AbstractEntityFormComponent<PersonParticipantService, PersonParticipantEntity> {
  protected eventId: number;
  protected paymentTypes: PaymentTypeListItem[] = [];

  public constructor(
    router: Router,
    errorService: ErrorService,
    personParticipantService: PersonParticipantService,
    private readonly activatedRoute: ActivatedRoute,
    private readonly paymentTypeService: PaymentTypeService,
  ) {
    super(router, errorService, personParticipantService);
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
      firstName: new FormControl(undefined, Validators.required),
      lastName: new FormControl(undefined, Validators.required),
      nationalIdentificationCode: new FormControl(undefined, Validators.required),
      paymentTypeId: new FormControl(undefined, Validators.required),
      additionalInformation: new FormControl(undefined),
    });
  }

  protected override onSubmit(personParticipantEntity: PersonParticipantEntity): void {
    if (personParticipantEntity.id) {
      this.subscribeOnce(this.entityService.modifyPersonParticipant(personParticipantEntity.id, personParticipantEntity), (): void => {
        this.successMessage = 'Muudetud';
      });
    } else {
      this.subscribeOnce(this.entityService.addPersonParticipant(this.eventId, personParticipantEntity), (): void => {
        this.reloadPage();
      });
    }
  }
}
