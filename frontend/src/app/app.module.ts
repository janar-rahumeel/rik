import { NgModule } from '@angular/core';
import { AppComponent } from './app.component';
import { ErrorStateMatcher, ShowOnDirtyErrorStateMatcher } from '@angular/material/core';
import { TitleStrategy } from '@angular/router';
import { AppTitleStrategy } from './application/core/app-title.strategy';
import { AppRoutingModule } from './app-routing.module';
import { PersonParticipantModule } from './domain/person-participant/person-participant.module';
import { AppSharedModule } from './application/shared/app-shared.module';
import { EventModule } from './domain/event/event.module';
import { LegalEntityParticipantModule } from './domain/legal-entity-participant/legal-entity-participant.module';
import { BrowserModule } from '@angular/platform-browser';
import { NgOptimizedImage } from '@angular/common';
import { InfrastructureModule } from './infrastructure/infrastructure.module';

@NgModule({
  declarations: [AppComponent],
  imports: [
    AppRoutingModule,
    AppSharedModule,
    BrowserModule,
    EventModule,
    InfrastructureModule,
    LegalEntityParticipantModule,
    NgOptimizedImage,
    PersonParticipantModule,
  ],
  providers: [
    { provide: ErrorStateMatcher, useClass: ShowOnDirtyErrorStateMatcher },
    { provide: TitleStrategy, useClass: AppTitleStrategy },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
