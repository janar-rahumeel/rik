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
import {AddEventComponent} from "./component/add-event/add-event.component";
import {AppRoutingModule} from "./app-routing.module";
import {CdkListbox} from "@angular/cdk/listbox";

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    AddEventComponent
  ],
    imports: [
        BrowserModule,
        HttpClientModule,
        RouterOutlet,
        RouterLink,
        NgOptimizedImage,
        RouterLinkActive,
        AppRoutingModule,
        CdkListbox
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
