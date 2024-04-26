import {Component, OnInit} from '@angular/core';
import {AbstractComponent} from "../../base.component";
import {FormBuilder, FormControl, FormGroup, ValidationErrors, Validators} from "@angular/forms";
import {EventService} from "../../../service/event.service";
import {Event} from "../../../generated/rik-backend";
import {ErrorService} from "../../../service/error.service";
import {DatePipe} from "@angular/common";
import {Router} from "@angular/router";

@Component({
  selector: 'home',
  templateUrl: './add-event.component.html',
  styleUrls: ['./add-event.component.css'],
  providers: [DatePipe]
})
export class AddEventComponent extends AbstractComponent implements OnInit {

  protected submitForm: FormGroup;

  constructor(
    private readonly router: Router,
    private readonly formBuilder: FormBuilder,
    private readonly errorService: ErrorService,
    private readonly eventService: EventService) {
    super();
  }

  ngOnInit(): void {
    this.submitForm = this.formBuilder.group({
      name: new FormControl('', Validators.required),
      startDateTime: new FormControl('', Validators.required),
      location: new FormControl('', Validators.required),
      description: new FormControl('', Validators.required)
    });
    this.errorService.resetFieldValidationErrors();
    this.subscribe(this.errorService.entityFieldValidationErrors$, (validationErrors: ValidationErrors): void => {
      Object.entries(validationErrors)
        .forEach(([fieldName, errorMessage]): void => {
          let transformedFieldName: string = fieldName.split(".")[1];
          if (this.submitForm.contains(transformedFieldName)) {
            let formFieldControl = this.submitForm.get(transformedFieldName);
            if (formFieldControl) {
              formFieldControl.markAsDirty({onlySelf: true});
              formFieldControl.setErrors({server: errorMessage});
            } else {
              alert(errorMessage);
            }
          }
        })
    });
  }

  onSubmit(): void {
    this.errorService.resetFieldValidationErrors();
    let event: Event = this.submitForm.getRawValue() as Event;
    this.subscribeOnce(this.eventService.createEvent(event), ignored => {
      this.router.navigate(['home']);
    });
  }

}
