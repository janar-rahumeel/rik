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
  templateUrl: './list-event-participants.component.html',
  styleUrls: ['./list-event-participants.component.css'],
  providers: [DatePipe]
})
export class ListEventParticipantsComponent extends AbstractComponent implements OnInit {

  constructor(
    private readonly router: Router,
    private readonly formBuilder: FormBuilder,
    private readonly errorService: ErrorService,
    private readonly eventService: EventService) {
    super();
  }

  ngOnInit(): void {

  }

}
