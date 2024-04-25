import {Component, OnInit} from '@angular/core';
import {AbstractComponent} from "../base.component";

@Component({
  selector: 'home',
  templateUrl: './add-event.component.html',
  styleUrls: ['./add-event.component.css']
})
export class AddEventComponent extends AbstractComponent implements OnInit {

  ngOnInit(): void {

  }

}
