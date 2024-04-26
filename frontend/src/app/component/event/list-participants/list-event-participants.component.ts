import {Component} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {EventService} from "../../../service/event.service";
import {ErrorResponse, Event, EventParticipant, PersonParticipant} from "../../../generated/rik-backend";
import {ErrorService} from "../../../service/error.service";
import {ActivatedRoute, Router} from "@angular/router";
import {AbstractEntityFormComponent} from "../../entity-form.component";
import {PersonParticipantService} from "../../../service/person-participant.service";

@Component({
  selector: 'list-event-participants',
  templateUrl: './list-event-participants.component.html',
  styleUrls: ['./list-event-participants.component.css']
})
export class ListEventParticipantsComponent extends AbstractEntityFormComponent<any> {

  protected eventId: number;
  protected event: Event;
  protected eventParticipants: EventParticipant[];
  private personParticipantFormGroup: FormGroup = new FormGroup({
    firstName: new FormControl(undefined, Validators.required),
    lastName: new FormControl(undefined, Validators.required),
    nationalIdentificationCode: new FormControl(undefined, Validators.required),
    paymentTypeId: new FormControl(undefined, Validators.required),
    additionalInformation: new FormControl(undefined)
  });

  constructor(errorService: ErrorService,
              private readonly activatedRoute: ActivatedRoute,
              private readonly formBuilder: FormBuilder,
              private readonly eventService: EventService,
              private readonly personParticipantService: PersonParticipantService,
              private readonly router: Router) {
    super(errorService);
  }

  protected override onInit(): void {
    this.eventId = this.activatedRoute.snapshot.params['id'];
    this.subscribeOnce(this.eventService.getEvent(this.eventId), (event: Event): void => {
      this.event = event;
    });
    this.subscribeOnce(this.eventService.getParticipants(this.eventId), (eventParticipants: EventParticipant[]): void => {
      this.eventParticipants = eventParticipants;
    })
    this.subscribe(this.errorService.globalErrors$, (errorResponse: ErrorResponse): void => {
      this.generalError = errorResponse.message || '';
    });
  }

  protected override getForm(): FormGroup {
    return this.personParticipantFormGroup;
  }

  protected override onSubmit(entity: any): void {
    let personParticipant: PersonParticipant = entity as PersonParticipant;
    this.subscribeOnce(this.eventService.addPersonParticipant(this.eventId, personParticipant), ignored => {
      this.router.navigate(['/event', this.eventId, 'participants']);
    });
  }

  protected onParticipantShowButtonClick(compositeId: string): void {
    if (compositeId.startsWith('PP-')) {
      let personParticipantId: number = Number(compositeId.replace('PP-', ''));
      this.router.navigate(['/person-participant/', personParticipantId]);
    }
  }

}
