import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { EventService } from '../../service/event.service';
import { Event } from '../../../../generated/rik-backend';
import { ErrorService } from '../../../../application/core/error.service';
import { Router } from '@angular/router';
import { AbstractEntityFormComponent } from '../../../../application/core/entity-form.component';
import { ViewComponent } from '../../../../application/shared/component/view.component';

@Component({
  selector: 'rik-add-event',
  templateUrl: './add-event.component.html',
})
export class AddEventComponent extends AbstractEntityFormComponent<EventService, Event> {
  constructor(
    router: Router,
    errorService: ErrorService,
    eventService: EventService,
    private readonly view: ViewComponent,
  ) {
    super(router, errorService, eventService);
  }

  protected override onInit(): void {
    this.view.getLabelSubject().next('Ürituse lisamine');
  }

  protected override getForm(): FormGroup {
    return new FormGroup({
      name: new FormControl(undefined, Validators.required),
      startDateTime: new FormControl(undefined, Validators.required),
      location: new FormControl(undefined, Validators.required),
      description: new FormControl(undefined, Validators.required),
    });
  }

  protected override onSubmit(entity: Event): void {
    this.subscribeOnce(this.entityService.createEvent(entity), (): void => {
      this.router.navigate(['home']);
    });
  }
}
