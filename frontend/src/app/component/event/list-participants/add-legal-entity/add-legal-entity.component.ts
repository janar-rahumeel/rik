import {Component} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {LegalEntityParticipant} from "../../../../generated/rik-backend";
import {ErrorService} from "../../../../service/error.service";
import {AbstractEntityFormComponent} from "../../../entity-form.component";
import {EventService} from "../../../../service/event.service";

@Component({
  selector: 'add-legal-entity',
  templateUrl: './add-legal-entity.component.html'
})
export class AddLegalEntityComponent extends AbstractEntityFormComponent<LegalEntityParticipant> {

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
      name: new FormControl(undefined, Validators.required),
      registrationCode: new FormControl(undefined, Validators.required),
      participantCount: new FormControl(undefined, Validators.required),
      paymentTypeId: new FormControl(undefined, Validators.required),
      additionalInformation: new FormControl(undefined)
    });
  }

  protected override onSubmit(legalEntityParticipant: LegalEntityParticipant): void {
    this.subscribeOnce(this.eventService.addLegalEntityParticipant(this.eventId, legalEntityParticipant), ignored => {
      this.reloadPage();
    });
  }

}
