import {NgModule} from '@angular/core';
import {AppComponent} from './app.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {ErrorHandlerHttpInterceptor} from "./shared/error-handler.interceptor";
import {ErrorStateMatcher, ShowOnDirtyErrorStateMatcher} from "@angular/material/core";
import {RouterLink, RouterLinkActive, RouterOutlet, TitleStrategy} from "@angular/router";
import {HomeComponent} from "./component/home/home.component";
import {BrowserModule} from "@angular/platform-browser";
import {NgOptimizedImage} from "@angular/common";
import {CustomTitleStrategy} from "./shared/custom.title-strategy";
import {AddEventComponent} from "./component/event/add/add-event.component";
import {AppRoutingModule} from "./app-routing.module";
import {CdkListbox} from "@angular/cdk/listbox";
import {ReactiveFormsModule} from "@angular/forms";
import {ListEventParticipantsComponent} from "./component/event/list-participants/list-event-participants.component";
import {PersonParticipantComponent} from "./component/person-participant/person-participant.component";
import {ViewComponent} from "./component/shared/view/view.component";
import {AddPersonComponent} from "./component/event/list-participants/add-person/add-person.component";
import {AddLegalEntityComponent} from "./component/event/list-participants/add-legal-entity/add-legal-entity.component";

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    ViewComponent,
    AddEventComponent,
    ListEventParticipantsComponent,
    AddPersonComponent,
    AddLegalEntityComponent,
    PersonParticipantComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    RouterOutlet,
    RouterLink,
    NgOptimizedImage,
    RouterLinkActive,
    AppRoutingModule,
    CdkListbox,
    ReactiveFormsModule
  ],
  providers: [
    {provide: HTTP_INTERCEPTORS, useClass: ErrorHandlerHttpInterceptor, multi: true},
    {provide: ErrorStateMatcher, useClass: ShowOnDirtyErrorStateMatcher},
    {provide: TitleStrategy, useClass: CustomTitleStrategy},
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
