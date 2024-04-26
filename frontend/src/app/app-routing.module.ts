import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from "./component/home/home.component";
import {AddEventComponent} from "./component/event/add/add-event.component";
import {ListEventParticipantsComponent} from "./component/event/list-participants/list-event-participants.component";

const routes: Routes = [
  {
    path: '',
    redirectTo: '/home',
    pathMatch: 'full'
  },
  {
    path: 'home',
    title: 'Avaleht',
    pathMatch: 'full',
    component: HomeComponent,
    children: []
  },
  {
    path: 'event/add',
    title: 'Lisa sündmus',
    pathMatch: 'full',
    component: AddEventComponent,
    children: []
  },
  {
    path: 'event/:id/participants',
    title: 'Osalejad',
    pathMatch: 'full',
    component: ListEventParticipantsComponent,
    children: []
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
