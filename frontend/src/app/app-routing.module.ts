import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from "./component/home/home.component";
import {AddEventComponent} from "./component/add-event/add-event.component";

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
    path: 'add-event',
    title: 'Lisa sündmus',
    pathMatch: 'full',
    component: AddEventComponent,
    children: []
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
