import { NgModule } from '@angular/core';
import { RouteReuseStrategy, RouterModule, Routes } from '@angular/router';
import { EventsComponent } from './domain/event/component/list/events.component';
import { AddEventComponent } from './domain/event/component/add/add-event.component';
import { EventParticipantsComponent } from './domain/event/component/participants/event-participants.component';
import { PersonParticipantComponent } from './domain/person-participant/component/person-participant.component';
import { ViewComponent } from './application/shared/component/view.component';
import { LegalEntityParticipantComponent } from './domain/legal-entity-participant/component/legal-entity-participant.component';
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
    children: [],
  },
  {
    path: '',
    title: '',
    pathMatch: 'prefix',
    component: ViewComponent,
    children: [
      {
        path: 'event/add',
        title: 'Lisa sündmus',
        pathMatch: 'full',
        component: AddEventComponent,
        children: [],
      },
      {
        path: 'event/:id/participants',
        title: 'Osalejad',
        pathMatch: 'full',
        component: EventParticipantsComponent,
        children: [],
      },
      {
        path: 'person-participant/:id',
        title: 'Osaleja',
        pathMatch: 'full',
        component: PersonParticipantComponent,
        children: [],
      },
      {
        path: 'legal-entity-participant/:id',
        title: 'Osaleja',
        pathMatch: 'full',
        component: LegalEntityParticipantComponent,
        children: [],
      },
    ],
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
