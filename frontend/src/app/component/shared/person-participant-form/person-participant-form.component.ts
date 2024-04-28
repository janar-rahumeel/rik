import {Component} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {PaymentTypeListItem, PersonParticipant} from "../../../generated/rik-backend";
import {ErrorService} from "../../../service/error.service";
import {AbstractEntityFormComponent} from "../../entity-form.component";
import {EventService} from "../../../service/event.service";
import {Subject} from "rxjs";
import {PersonParticipantService} from "../../../service/person-participant.service";
import {PaymentTypeService} from "../../../service/payment-type.service";

@Component({
  selector: 'person-participant-form',
  templateUrl: './person-participant-form.component.html'
})
export class PersonParticipantFormComponent extends AbstractEntityFormComponent<PersonParticipantEntity> {

  protected eventId: number;
  protected paymentTypes: PaymentTypeListItem[] = [];

  constructor(router: Router,
              errorService: ErrorService,
              private readonly activatedRoute: ActivatedRoute,
              private readonly paymentTypeService: PaymentTypeService,
              private readonly personParticipantService: PersonParticipantService,
              private readonly eventService: EventService) {
    super(router, errorService);
  }

  public getEntitySubject(): Subject<PersonParticipantEntity> {
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
      firstName: new FormControl(undefined, Validators.required),
      lastName: new FormControl(undefined, Validators.required),
      nationalIdentificationCode: new FormControl(undefined, Validators.required),
      paymentTypeId: new FormControl(undefined, Validators.required),
      additionalInformation: new FormControl(undefined)
    });
  }

  protected override onSubmit(personParticipantEntity: PersonParticipantEntity): void {
    if (personParticipantEntity.id) {
      this.subscribeOnce(this.personParticipantService.modifyPersonParticipant(personParticipantEntity.id, personParticipantEntity), ignored => {
        this.successMessage = 'Muudetud';
      });
    } else {
      this.subscribeOnce(this.eventService.addPersonParticipant(this.eventId, personParticipantEntity), ignored => {
        this.reloadPage();
      });
    }
  }

}

export interface PersonParticipantEntity extends PersonParticipant {
  id?: number;
}
