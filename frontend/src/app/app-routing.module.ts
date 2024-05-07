import { NgModule } from '@angular/core';
import { RouteReuseStrategy, RouterModule, Routes } from '@angular/router';
import { EventsComponent } from './domain/event/component/list/events.component';
import { AddEventComponent } from './domain/event/component/add/add-event.component';
import { EventParticipantsComponent } from './domain/event/component/participants/event-participants.component';
import { PersonParticipantComponent, resolvePersonParticipant } from './domain/person-participant/component/person-participant.component';
import {
  LegalEntityParticipantComponent,
  resolveLegalEntityParticipant,
} from './domain/legal-entity-participant/component/legal-entity-participant.component';
import { AppRouteReuseStrategy } from './application/core/app-route-resuse.strategy';

const routes: Routes = [
  {
    path: '',
    redirectTo: '/home',
    pathMatch: 'full',
  },
  {
    path: 'home',
    title: 'Avaleht',
    pathMatch: 'full',
    component: EventsComponent,
  },
  {
    path: 'event/add',
    title: 'Lisa sündmus',
    pathMatch: 'full',
    component: AddEventComponent,
  },
  {
    path: 'event/:id/participants',
    title: 'Osalejad',
    pathMatch: 'full',
    component: EventParticipantsComponent,
  },
  {
    path: 'person-participant/:id',
    title: 'Osaleja',
    pathMatch: 'full',
    component: PersonParticipantComponent,
    resolve: { entity: resolvePersonParticipant },
  },
  {
    path: 'legal-entity-participant/:id',
    title: 'Osaleja',
    pathMatch: 'full',
    component: LegalEntityParticipantComponent,
    resolve: { entity: resolveLegalEntityParticipant },
  },
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes, {
      onSameUrlNavigation: 'reload',
    }),
  ],
  providers: [{ provide: RouteReuseStrategy, useClass: AppRouteReuseStrategy }],
  exports: [RouterModule],
})
export class AppRoutingModule {}
