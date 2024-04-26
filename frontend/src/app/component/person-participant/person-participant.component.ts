import {Component} from '@angular/core';
import {FormGroup} from "@angular/forms";
import {DatePipe} from "@angular/common";
import {Router} from "@angular/router";
import {AbstractEntityFormComponent} from "../entity-form.component";
import {PersonParticipant} from "../../generated/rik-backend";
import {ErrorService} from "../../service/error.service";
import {PersonParticipantService} from "../../service/person-participant.service";

@Component({
  selector: 'person-participant',
  templateUrl: './person-participant.component.html',
  styleUrls: ['./person-participant.component.css']
})
export class PersonParticipantComponent extends AbstractEntityFormComponent<PersonParticipant> {

  public constructor(errorService: ErrorService,
                     private readonly personParticipantService: PersonParticipantService,
                     private readonly router: Router) {
    super(errorService); // TODO
  }

  protected getForm(): FormGroup {
    return new FormGroup({});
  }

  protected onInit(): void {
  }

  protected onSubmit(personParticipant: PersonParticipant): void {
  }

}
