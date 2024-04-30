import { Component, Optional } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { EventService } from '../../../service/event.service';
import { Event } from '../../../generated/rik-backend';
import { ErrorService } from '../../../service/error.service';
import { Router } from '@angular/router';
import { AbstractEntityFormComponent } from '../../entity-form.component';
import { ViewComponent } from '../../shared/view/view.component';

@Component({
  selector: 'rik-add-event',
  templateUrl: './add-event.component.html',
})
export class AddEventComponent extends AbstractEntityFormComponent<Event> {
  constructor(
    router: Router,
    errorService: ErrorService,
    @Optional() private readonly view: ViewComponent,
    private readonly eventService: EventService,
  ) {
    super(router, errorService);
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
    this.subscribeOnce(this.eventService.createEvent(entity), (): void => {
      this.router.navigate(['home']);
    });
  }
}
