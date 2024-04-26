import {Component} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {PersonParticipant} from "../../../../generated/rik-backend";
import {ErrorService} from "../../../../service/error.service";
import {AbstractEntityFormComponent} from "../../../entity-form.component";
import {EventService} from "../../../../service/event.service";

@Component({
  selector: 'add-person',
  templateUrl: './add-person.component.html'
})
export class AddPersonComponent extends AbstractEntityFormComponent<PersonParticipant> {

  protected eventId: number;

  constructor(router: Router,
              errorService: ErrorService,
              private readonly activatedRoute: ActivatedRoute,
              private readonly eventService: EventService) {
    super(router, errorService);
  }

  protected override onInit(): void {
    this.eventId = this.activatedRoute.snapshot.params['id'];
  }

  protected override getForm(): FormGroup {
    return new FormGroup({
      firstName: new FormControl(undefined, Validators.required),
      lastName: new FormControl(undefined, Validators.required),
      nationalIdentificationCode: new FormControl(undefined, Validators.required),
      paymentTypeId: new FormControl(undefined, Validators.required),
      additionalInformation: new FormControl(undefined)
    });
  }

  protected override onSubmit(personParticipant: PersonParticipant): void {
    this.subscribeOnce(this.eventService.addPersonParticipant(this.eventId, personParticipant), ignored => {
      this.reloadPage();
    });
  }

}
