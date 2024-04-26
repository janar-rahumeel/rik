import {Component} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {EventService} from "../../../service/event.service";
import {Event} from "../../../generated/rik-backend";
import {ErrorService} from "../../../service/error.service";
import {DatePipe} from "@angular/common";
import {Router} from "@angular/router";
import {AbstractEntityFormComponent} from "../../entity-form.component";

@Component({
  selector: 'add-event',
  templateUrl: './add-event.component.html',
  styleUrls: ['./add-event.component.css'],
  providers: [DatePipe]
})
export class AddEventComponent extends AbstractEntityFormComponent<Event> {

  constructor(
    errorService: ErrorService,
    private readonly eventService: EventService,
    private readonly router: Router) {
    super(errorService);
  }

  protected override onInit(): void {
    // TODO
  }

  protected override getForm(): FormGroup {
    return new FormGroup({
      name: new FormControl(undefined, Validators.required),
      startDateTime: new FormControl(undefined, Validators.required),
      location: new FormControl(undefined, Validators.required),
      description: new FormControl(undefined, Validators.required)
    });
  }

  protected override onSubmit(entity: Event): void {
    this.subscribeOnce(this.eventService.createEvent(entity), (): void => {
      this.router.navigate(['home']);
    });
  }

}
