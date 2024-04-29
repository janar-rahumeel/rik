import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './component/home/home.component';
import { AddEventComponent } from './component/event/add/add-event.component';
import { ListEventParticipantsComponent } from './component/event/list-participants/list-event-participants.component';
import { PersonParticipantComponent } from './component/person-participant/person-participant.component';
import { ViewComponent } from './component/shared/view/view.component';
import { LegalEntityParticipantComponent } from './component/legal-entity-participant/legal-entity-participant.component';

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
    component: HomeComponent,
    children: [],
  },
  {
    path: 'view',
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
        component: ListEventParticipantsComponent,
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
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class RikRoutingModule {}
