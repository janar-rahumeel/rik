import { NgModule } from '@angular/core';
import { PersonParticipantModule } from '../person-participant/person-participant.module';
import { EventParticipantsComponent } from './component/participants/event-participants.component';
import { EventService } from './service/event.service';
import { AddEventComponent } from './component/add/add-event.component';
import { LegalEntityParticipantModule } from '../legal-entity-participant/legal-entity-participant.module';
import { EventsComponent } from './component/list/events.component';
import { RouterLink } from '@angular/router';
import { NgOptimizedImage } from '@angular/common';
import { AppSharedModule } from '../../application/shared/app-shared.module';

@NgModule({
  imports: [AppSharedModule, LegalEntityParticipantModule, NgOptimizedImage, PersonParticipantModule, RouterLink],
  declarations: [EventsComponent, EventParticipantsComponent, AddEventComponent],
  providers: [EventService],
})
export class EventModule {}
